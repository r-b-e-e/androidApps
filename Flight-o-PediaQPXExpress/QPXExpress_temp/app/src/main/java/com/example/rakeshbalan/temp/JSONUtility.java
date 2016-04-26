package com.example.rakeshbalan.temp;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


public class JSONUtility {
	public static class JSONFunctionClass {
		public static HashMap<String, Airport> getAirport(JSONObject jsonObject) throws JSONException {
			HashMap<String, Airport> hashMapAirport = new HashMap<String, Airport>();
			
			JSONObject trips = jsonObject.getJSONObject("trips");
			JSONObject data = trips.getJSONObject("data");
			JSONArray airport = data.getJSONArray("airport");
			for(int i=0;i<airport.length();i++)
			{
				JSONObject airportObject = airport.getJSONObject(i);
				Airport airport1 = new Airport();
				airport1.setAirportCity(airportObject.getString("city"));
				airport1.setAirportName(airportObject.getString("name"));

				hashMapAirport.put(airportObject.getString("code"), airport1);
//				Log.d("demo", airportObject.getString("city") + " " + airportObject.getString("name") + " " + airportObject.getString("code"));
			}
			
			return hashMapAirport;
		}

		public static HashMap<String, String> getCity(JSONObject jsonObject) throws JSONException {
			HashMap<String, String> hashMapCity = new HashMap<String, String>();

			JSONObject trips = jsonObject.getJSONObject("trips");
			JSONObject data = trips.getJSONObject("data");
			JSONArray city = data.getJSONArray("city");
			for(int i=0;i<city.length();i++)
			{
				JSONObject cityObject = city.getJSONObject(i);
				hashMapCity.put(cityObject.getString("code"), cityObject.getString("name"));
			}

			return hashMapCity;
		}

		public static HashMap<String, String> getCarrier(JSONObject jsonObject) throws JSONException {
			HashMap<String, String> hashMapCarrier = new HashMap<String, String>();

			JSONObject trips = jsonObject.getJSONObject("trips");
			JSONObject data = trips.getJSONObject("data");
			JSONArray carrier = data.getJSONArray("carrier");
			for(int i=0;i<carrier.length();i++)
			{
				JSONObject carrierObject = carrier.getJSONObject(i);
				hashMapCarrier.put(carrierObject.getString("code"), carrierObject.getString("name"));
			}

			return hashMapCarrier;
		}

		public static List<Option> getOptionList(JSONObject jsonObject, Integer noOfAdults, Integer noOfChild) throws JSONException {
			List<Option> optionList = new ArrayList<Option>();

			JSONObject trips = jsonObject.getJSONObject("trips");
			JSONArray tripOption = trips.getJSONArray("tripOption");

			for(int i=0; i<tripOption.length();i++)
			{
				JSONObject tripOptionObject = tripOption.getJSONObject(i);
				Option option = new Option();

				option.setOptionId(tripOptionObject.getString("id"));
				option.setSaleTotal(tripOptionObject.getString("saleTotal"));

				JSONArray pricing = tripOptionObject.getJSONArray("pricing");

				Price price = new Price();
				JSONObject pricingAdult = pricing.getJSONObject(0);
				price.setSaleFareTotalAdult(pricingAdult.getString("saleFareTotal"));
				price.setSaleTaxTotalAdult(pricingAdult.getString("saleTaxTotal"));
				price.setSaleTotalAdult(pricingAdult.getString("saleTotal"));

				if(noOfChild != 0)
				{
					JSONObject pricingChild = pricing.getJSONObject(1);
					price.setSaleFareTotalChild(pricingChild.getString("saleFareTotal"));
					price.setSaleTaxTotalChild(pricingChild.getString("saleTaxTotal"));
					price.setSaleTotalChild(pricingChild.getString("saleTotal"));
				}
				else
				{
					price.setSaleFareTotalChild("0");
					price.setSaleTaxTotalChild("0");
					price.setSaleTotalChild("0");
				}
				option.setPrice(price);
				option.setNoOfAdults(noOfAdults);
				option.setNoOfChild(noOfChild);


				JSONArray slice = tripOptionObject.getJSONArray("slice");
				JSONObject sliceObject = slice.getJSONObject(0);
				option.setTotalFlightDuration(sliceObject.getInt("duration"));
				JSONArray segment = sliceObject.getJSONArray("segment");

				List<Flight> flightList= new ArrayList<Flight>();

				for(int j=0; j<segment.length(); j++)
				{
					JSONObject segmentObject = segment.getJSONObject(j);
					Flight flightObject = new Flight();

					flightObject.setFlightDuration(segmentObject.getInt("duration"));

					if (j==segment.length()-1)
					{
						flightObject.setConnectionDuration(0);
					}
					else
					{
						flightObject.setConnectionDuration(segmentObject.getInt("connectionDuration"));
					}

					JSONObject flight = segmentObject.getJSONObject("flight");
					JSONArray leg = segmentObject.getJSONArray("leg");

					flightObject.setFlightCarrier(flight.getString("carrier"));
					flightObject.setFlightNumber(flight.getString("number"));

					JSONObject legObject = leg.getJSONObject(0);
					flightObject.setArrivalTime(legObject.getString("arrivalTime"));
					flightObject.setDepartureTime(legObject.getString("departureTime"));
					flightObject.setOrigin(legObject.getString("origin"));
					flightObject.setDestination(legObject.getString("destination"));

					flightList.add(flightObject);
				}
				option.setFlightList(flightList);

				optionList.add(option);
			}

			return optionList;
		}
	}
}

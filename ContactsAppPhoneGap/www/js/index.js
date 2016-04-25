/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

    
function saveContact()
{
    var displayName = document.getElementById("name").value;
    var email = document.getElementById("email").value;
    var url = document.getElementById("url").value;
    
    var workTel = document.getElementById("work").value
    var mobileTel =document.getElementById("mobile").value;
    var homeTel = document.getElementById("home").value;
    console.log("@@saving : "+displayName);
    
    var myContact = navigator.contacts.create({"displayName": displayName});
    
    myContact.name = name;
    
    var phoneNumbers = [];
phoneNumbers[0] = new ContactField('work', workTel, true);
phoneNumbers[1] = new ContactField('mobile', mobileTel, true); 
phoneNumbers[2] = new ContactField('home', homeTel, true);
myContact.phoneNumbers = phoneNumbers;
    
    myContact.save(function(){
    alert("Contact saved successfully");
        navigator.notification.alert(
                            'Contact saved successfully',  // message
                            function(){},         // callback
                            'ContactsApp',            // title
                            'OK'                  // buttonName
                        );
    }, function(msg){
    alert("Error saving contact: "+msg);
        navigator.notification.alert(
                            "Error saving contact: "+msg,  // message
                            function(){},         // callback
                            'ContactsApp',            // title
                            'OK'                  // buttonName
                        );
    });  //HERE
    console.log("The contact, " + myContact.displayName);
}

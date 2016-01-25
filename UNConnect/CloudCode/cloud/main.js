Parse.Cloud.afterSave(Parse.User, function (request) {
    console.log("Installation of " + request.object.get("username"));
    var name = "";
    name = request.object.get("username");
    if (!(undefined != name)) {
        console.log("No push cause user is updating")
    } else {
        console.log("the name is: " + name)
        var install = new Parse.Object.extend("Installation");
        var q2 = new Parse.Query(install);
       /* q2.equalTo("privacy", privateUserNo);*/
        q2.find({
            success: function (results) {
                Parse.Push.send({
                    where: q2, // Set our Installation query
                    data: {
                        alert: "New User " + request.object.get("username") + " has signed up!"
                    }
                }, {
                    success: function () {
                        // Push was successful
                        console.log("Push success");
                    },
                    error: function (error) {
                        // Handle error
                        console.log("Push error");
                    }
                })
            },
            error: function (error) {
                console.log("Error: " + error.code + " " + error.message);
            }
        });
    }

});



/**
 * This is to send a pushNotification to user when a message is sent to the user
 * by another user
 */
Parse.Cloud.afterSave("Message", function (request) {
    var to = request.object.get("receivername");
    var from = request.object.get("sendername");
    //Get from Name


    console.log("Message sent to " + request.object.get("receivername") + "sent from " + from);

    //get installation and push
    var install = new Parse.Object.extend("Installation");
    var q2 = new Parse.Query(install);
    q2.equalTo("username", to);
   // q2.equalTo("privacy", privateUserNo);
    q2.find({
        success: function (results) {
            Parse.Push.send({
                where: q2, // Set our Installation query
                data: {
                    alert: "Hey! " + request.object.get("receivername") + " you have new message from " + from
                }
            }, {
                success: function () {
                    // Push was successful
                    console.log("Push success");
                },
                error: function (error) {
                    // Handle error
                    console.log("Push error");
                }
            })
        },
        error: function (error) {
            console.log("Error: " + error.code + " " + error.message);
        }
    });
});
/**
 * Whenever a new album is added to the Album it sends a push notification
 * to the shared user
 */
 Parse.Cloud.afterSave("Album", function (request) {
    console.log(request.object.get("createdAt").getTime() + " Created At");
    console.log(request.object.get("updatedAt").getTime() + " Updated At");
    if (request.object.get("createdAt").getTime() == request.object.get("updatedAt").getTime()) {
        //TODO: Firse Save,  may be add when he adds all
        console.log("Initially save");
    } else {
        //TODO: Already existed Get the last user and try!
        console.log("Upda");

        var Album = Parse.Object.extend("Album");
        var query = new Parse.Query(Album);
        query.include(ParseUser.class);
		query.where("sharedUsers")
        query.descending("updatedAt")
        query.first({
            success: function (results) {
                console.log("Successfully retrieved " + results.length + " scores. testing an old : ");
                // Do something with the returned Parse.Object values
                //var object = results.get("creatorID");
                var allowedUsers = results.get("sharedUsers");
                var albumName = results.get("albumTitle");
                var ownerName = results.get("albumOwner");
                console.log("The new user invited" + allowedUsers[allowedUsers.length - 1]);
                //Get Installation for user
                //console.log("Installation of " + request.object.get("username"));
                alert("Hello! " + allowedUsers[allowedUsers.length - 1] + " you have been invited to " +
                    albumName + " by: " + ownerName);
                var install = new Parse.Object.extend("Installation");
                var q2 = new Parse.Query(install);
                q2.equalTo("username", allowedUsers[allowedUsers.length - 1]);
                q2.equalTo("privacy", privateUserNo);
                q2.find({
                    success: function (results) {
                        Parse.Push.send({
                            where: q2, // Set our Installation query
                            data: {
                                alert: "Hello! " + allowedUsers[allowedUsers.length - 1] + " you have been invited to " + +albumName + " by: " + ownerName
                            }
                        }, {
                            success: function () {
                                // Push was successful
                                console.log("Push success");
                            },
                            error: function (error) {
                                // Handle error
                                console.log("Push error");
                            }
                        })
                    },
                    error: function (error) {
                        console.log("Error: " + error.code + " " + error.message);
                    }
                });
            },
            error: function (error) {
                console.log("Error: " + error.code + " " + error.message);
            }
        });
    }
});

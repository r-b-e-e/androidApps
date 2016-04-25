//Developed by Rakesh Balan

// Initialize your app
var myApp = new Framework7();

// Export selectors engine
var $$ = Dom7;

// Add view
var mainView = myApp.addView('.view-main', {
    // Because we use fixed-through navbar we can enable dynamic navbar
    dynamicNavbar: true
});

// Callbacks to run specific code for specific pages, for example for About page:
myApp.onPageInit('about', function (page) {
    // run createContentPage func after link was clicked
    $$('.create-page').on('click', function () {
        createContentPage();
    });
});

// Generate dynamic page
var dynamicPageIndex = 0;
function createContentPage() {
	mainView.router.loadContent(
        '<!-- Top Navbar-->' +
        '<div class="navbar">' +
        '  <div class="navbar-inner">' +
        '    <div class="left"><a href="#" class="back link"><i class="icon icon-back"></i><span>Back</span></a></div>' +
        '    <div class="center sliding">Dynamic Page ' + (++dynamicPageIndex) + '</div>' +
        '  </div>' +
        '</div>' +
        '<div class="pages">' +
        '  <!-- Page, data-page contains page name-->' +
        '  <div data-page="dynamic-pages" class="page">' +
        '    <!-- Scrollable page content-->' +
        '    <div class="page-content">' +
        '      <div class="content-block">' +
        '        <div class="content-block-inner">' +
        '          <p>Here is a dynamic page created on ' + new Date() + ' !</p>' +
        '          <p>Go <a href="#" class="back">back</a> or go to <a href="services.html">Services</a>.</p>' +
        '        </div>' +
        '      </div>' +
        '    </div>' +
        '  </div>' +
        '</div>'
    );
	return;
}



function saveContact()
{
    var displayName = document.getElementById("name").value;
    var email = document.getElementById("email").value;
    var url = document.getElementById("url").value;
    
    var workTel = document.getElementById("work").value
    var mobileTel =document.getElementById("mobile").value;
    var homeTel = document.getElementById("home").value;
    var day = document.getElementById("birthday").value;
    console.log("@@saving : "+displayName);
    
    var myContact = navigator.contacts.create({"displayName": displayName});
    
    myContact.name = name;
    myContact.birthday = day;
    var phoneNumbers = [];
    phoneNumbers[0] = new ContactField('mobile', mobileTel, true); 
	phoneNumbers[1] = new ContactField('work', workTel, true);
	phoneNumbers[2] = new ContactField('home', homeTel, true);
	myContact.phoneNumbers = phoneNumbers;
    var urls = [];
	var emails = [];
	urls[0] = new ContactField('url', url, false);
	emails[0] = new ContactField('email', email, false);
	myContact.emails = emails;
    myContact.urls = urls;
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

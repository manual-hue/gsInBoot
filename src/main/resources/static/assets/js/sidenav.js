    function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
}

    function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}

    // When the user scrolls the page, execute myFunction
    window.onscroll = function() {myFunction()};

    // Get the navbar
    var navbar = document.getElementById("navbar");

    // Get the offset position of the navbar
    var sticky = navbar.offsetTop;

    // Add the sticky class to the navbar when you reach its scroll position. Remove "sticky" when you leave the scroll position
    function myFunction() {
        if (window.pageYOffset >= sticky) {
            navbar.classList.add("sticky")
        } else {
            navbar.classList.remove("sticky");
        }
    }

//    서랍 사이드바
    var coll = document.getElementsByClassName("collapsible");
    var i;

    for (i = 0; i < coll.length; i++) {
        coll[i].addEventListener("click", function() {
            this.classList.toggle("active");
            var contentQNA = this.nextElementSibling;
            if (contentQNA.style.display === "block") {
                contentQNA.style.display = "none";
            } else {
                contentQNA.style.display = "block";
            }
        });
    }


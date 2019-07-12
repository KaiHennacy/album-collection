const NavBar = require("./components/NavBar");

const navBar = new NavBar();

const header = document.getElementsByTagName("header")[0];

header.appendChild(navBar.createNavBar());

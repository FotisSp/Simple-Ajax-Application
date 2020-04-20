//Get the button
var topBtn = document.getElementById("topBtn");

//When the user scrolls down 20px from the top of the document, show the button
window.onscroll = function() {scrollFunction()};

function scrollFunction() {
	if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
		topBtn.style.display = "block";
	} else {
		topBtn.style.display = "none";
	}
}

//When the user clicks on the button, scroll to the top of the document
function topFunction() {
	const scrollToTop = () => {
		  const c = document.documentElement.scrollTop || document.body.scrollTop;
		  if (c > 0) {
		    window.requestAnimationFrame(scrollToTop);
		    window.scrollTo(0, c - c / 3);
		  }
		};
		scrollToTop();
}


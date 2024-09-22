let currentIndex = 0;
const slides = document.querySelectorAll('.slides img');
const totalSlides = slides.length;

function showSlide(index) {
    if (index >= totalSlides) {
        currentIndex = 0;  // Loop back to the first slide
    } else if (index < 0) {
        currentIndex = totalSlides - 1;  // Loop back to the last slide
    } else {
        currentIndex = index;
    }
    const offset = -currentIndex * 100;
    document.querySelector('.slides').style.transform = `translateX(${offset}%)`;
}

function changeSlide(step) {
    showSlide(currentIndex + step);
}

document.querySelector('.prev').addEventListener('click', () => {
    changeSlide(-1);
});

document.querySelector('.next').addEventListener('click', () => {
    changeSlide(1);
});

// Initialize the slider to show the current slide
showSlide(currentIndex);

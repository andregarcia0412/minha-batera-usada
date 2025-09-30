import "../css/main.css"

const carouselImages = document.querySelector('.carousel-images')
const images = document.querySelectorAll('.carousel-images img')
const prevBtn = document.getElementById('prev')
const nextBtn = document.getElementById('next')

let index = 0

function showSlide(i){
    index = (i+images.length) % images.length
    carouselImages.style.transform = `TranslateX(${-index * 100}%)`
}

prevBtn.addEventListener('click', () => showSlide(index - 1))
nextBtn.addEventListener('click', () => showSlide(index + 1))

setInterval(() => showSlide(index+1),10000)
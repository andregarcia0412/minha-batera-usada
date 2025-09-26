async function signUp() {
    const name = document.getElementById("name").value
    const email = document.getElementById("email").value
    const password = document.getElementById("password").value
    const errorP = document.getElementById("error-p")

    if(name == ""){
        errorP.style.display = "block"
        errorP.textContent = "Please enter your name"
        return
    } else if(!/@.+$/.test(email)){
        errorP.style.display = "block"
        errorP.textContent = "Please enter a valid email address"
        return
    } else if(password == ""){
        errorP.style.display = "block"
        errorP.textContent = "Please enter your password"
        return
    } else if(password.length < 8){
        errorP.style.display = "block"
        errorP.textContent = "Password must be at least 8 characters long"
        return
    }

    const response = await fetch('http://localhost:8080/user', {
        method:'POST',
        headers:{
            'Content-Type':'application/json'
        },
        body: JSON.stringify({email:email, password:password, name:name})
    })

    const data = await response.json()
    if (data == true){
        window.location.href = "../index.html"
    } else {
        errorP.style.display = "block"
        errorP.textContent = "An account with this email already exists"
    }
}

async function signIn(){
    const email = document.getElementById("email").value
    const password = document.getElementById("password").value
    const errorP = document.getElementById("error-p")

    if(!/@.+$/.test(email)){
        errorP.style.display = "block"
        errorP.textContent = "Please enter a valid email address"
        return
    } else if(password == ""){
        errorP.style.display = "block"
        errorP.textContent = "Please enter your password"
        return
    } else if(password.length < 8){
        errorP.style.display = "block"
        errorP.textContent = "Password must be at least 8 characters long"
        return
    }

    const response = await fetch('http://localhost:8080/user/login', {
        method:'POST',
        headers:{
            'Content-Type':'application/json'
        },
        body: JSON.stringify({email:email, password:password})
    })

    const data = await response.json()
    if (data == true){
        window.location.href = "../index.html"
    } else{
        errorP.style.display = "block"
        errorP.textContent = "Incorrect email or password"
    }

}
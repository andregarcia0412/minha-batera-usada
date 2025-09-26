async function signUp() {
    const name = document.getElementById("name").value
    const email = document.getElementById("email").value
    const password = document.getElementById("password").value

    const response = await fetch('http://localhost:8080/user', {
        method:'POST',
        headers:{
            'Content-Type':'application/json'
        },
        body: JSON.stringify({email:email, password:password, name:name})
    })

    const data = await response.text()
    console.log(data)
}

async function signIn(){
    const email = document.getElementById("email").value
    const password = document.getElementById("password").value

    const response = await fetch('http://localhost:8080/user/login', {
        method:'POST',
        headers:{
            'Content-Type':'application/json'
        },
        body: JSON.stringify({email:email, password:password})
    })

    const data = await response.text()
    console.log(data)
}
const authorizedID = document.getElementById("authorizedID").textContent;
const url = "http://localhost:8080/api/users/" + authorizedID;
fetch(url)
    .then(res => res.json())
    .then(user => {
        document.getElementById("idUser").innerHTML = user.id;
        document.getElementById("userNameUser").innerHTML = user.username;
        document.getElementById("lastNameUser").innerHTML = user.lastName;
        document.getElementById("ageUser").innerHTML = user.age;
        document.getElementById("emailUser").innerHTML = user.email;
        let roles ="";
        for(let i=0; i<user.roles.length;i++) {
            roles+=user.roles[i].role.replace("ROLE_","") + " ";
        }
        document.getElementById("rolesUser").innerHTML = roles;
        document.getElementById("headEmail").innerHTML = user.email;
        document.getElementById("headRole").innerHTML = roles;
})       



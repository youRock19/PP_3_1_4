const authorizedID = document.getElementById("authorizedID").textContent;
const url = "http://localhost:8080/api/users/";

//форма создания нового пользователя
const form = document.forms.addUserForm;
const userTables = document.getElementById("userTables");

getAllUsers();

//запрос пользователей из сети
function getAllUsers() {
    fetch(url)
    .then(response => response.json())
    .then(users => userInfo(users))
}
//метод заполнения таблицы с информацией о пользователях
function userInfo (users) {
    let info = "";        
    for(let i=0; i<users.length; i++){
        let roles = "";
            for(let j=0; j<users[i].roles.length;j++) {
                (j<(users[i].roles.length-1))? roles+=users[i].roles[j].role.replace("ROLE_","") + " " : roles+=users[i].roles[j].role.replace("ROLE_","")
            }
        info+=`<tr class="align-middle">
        <td>${users[i].id}</td>
        <td>${users[i].username}</td>
        <td>${users[i].lastName}</td>
        <td>${users[i].age}</td>
        <td>${users[i].email}</td>
        <td>${roles}</td>
        <td><button class="btn btn-info" data-bs-toggle="modal" data-bs-target="#edit-modal" data-bs-id="${users[i].id}" data-bs-username="${users[i].username}" data-bs-lastname="${users[i].lastName}" data-bs-age="${users[i].age}" data-bs-email="${users[i].email}" data-bs-password="${users[i].password}" data-bs-roles="${roles}">Edit</button></td>
        <td><button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#delete-modal" data-bs-id="${users[i].id}" data-bs-username="${users[i].username}" data-bs-lastname="${users[i].lastName}" data-bs-age="${users[i].age}" data-bs-email="${users[i].email}" data-bs-roles="${roles}">Delete</button></td>`;
        
        if(Number(authorizedID)===users[i].id) {
                    document.getElementById("headEmail").innerHTML = users[i].email;
                    document.getElementById("headRole").innerHTML = roles;
        }  
    }
    document.getElementById("tableUsers").innerHTML = info; 
}


//прослушивание событий на создание нового пользователя
form.addEventListener("submit", (event) => {
    event.preventDefault();
    postData(url, createUserFromForm(form))
    .then(() => {
        document.location.reload();
        userTables.click();
    })
})

//метод создания нового пользователя по заполненной форме
function createUserFromForm (form) {

    let roles = []
    for(let i = 0; i < form.elements.roles.length; i++) {
        if(form.elements.roles[i].selected) {
        role = {
            id: form.elements.roles[i].value, 
            role: "ROLE_".concat(form.elements.roles[i].textContent)
        }
        roles.push(role)
        }
    }

    user = {
        username: form.elements.username.value,
        lastName: form.elements.lastName.value,
        age: form.elements.age.value,
        email: form.elements.email.value,
        password: form.elements.password.value,
        roles: roles
    }
    return user;
}

// метод выполнения post запроса
async function postData(url, user) {
    
    const response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    })
}


let flagDelete = 0;
//заполение окна удаления пользователя
let deleteModal=document.getElementById("delete-modal")
deleteModal.addEventListener('show.bs.modal', function (event) {
    if (flagDelete++ === 0) {
        var button = event.relatedTarget
        document.getElementById("delete-username").value = button.getAttribute("data-bs-username")
        document.getElementById("delete-lastname").value = button.getAttribute("data-bs-lastname")
        document.getElementById("delete-age").value = button.getAttribute("data-bs-age")
        document.getElementById("delete-email").value = button.getAttribute("data-bs-email")
        let deleteRoles = document.getElementById("delete-roles")
        let deleteRolesAttr = button.getAttribute("data-bs-roles").split(" ")
        for (let i = 0; i < deleteRolesAttr.length; i++) {
            let opt = document.createElement("option")
            opt.value = i + 1
            opt.text = deleteRolesAttr[i]
            deleteRoles.appendChild(opt)
        }
        deleteModal.addEventListener("hide.bs.modal", () => {
            flagDelete = 0
            deleteRoles.innerHTML = ""
        })
//прослушивание событий на удаление пользователя
        document.getElementById("deleteUserForm").addEventListener("submit", (event) => {
            event.preventDefault();
            deleteData(url, button.getAttribute("data-bs-id"))
                    .then(() => {
                        document.location.reload();
                        flagDelete = 0;
                    })
        })
    }
})




// метод удаления пользователя
async function deleteData(url, id) {
    
    const response = await fetch(`${url}${id}/`, {
        method: 'DELETE'
    })
}

let flagEdit = 0;
//заполение окна редактируемого пользователя
let editModal = document.getElementById("edit-modal")
editModal.addEventListener('show.bs.modal', function (event) {
    if (flagEdit++ === 0) {
        var button = event.relatedTarget
        document.getElementById("edit-id").value = button.getAttribute("data-bs-id")
        document.getElementById("edit-username").value = button.getAttribute("data-bs-username")
        document.getElementById("edit-lastname").value = button.getAttribute("data-bs-lastname")
        document.getElementById("edit-age").value = button.getAttribute("data-bs-age")
        document.getElementById("edit-email").value = button.getAttribute("data-bs-email")
        document.getElementById("edit-password").value = button.getAttribute("data-bs-password")
        let editRoles = document.getElementById("edit-roles")
        let editRolesAttr = button.getAttribute("data-bs-roles").split(" ")
        for (let i = 0; i < editRoles.length; i++) {
            for (let j = 0; j < editRolesAttr.length; j++) {
                if (editRoles[i].textContent.replace("ROLE_", "") === editRolesAttr[j]) {
                    editRoles[i].selected = true;
                }
            }
        }
        editModal.addEventListener("hide.bs.modal", () => {
            flagEdit = 0
            for (let i = 0; i < editRoles.length; i++) {
                editRoles[i].selected = false;
            }
        })
//прослушивание событий на редактирование пользователя
        document.getElementById("editUserForm").addEventListener("submit", (event) => {
            event.preventDefault();
            putData(url, editUserFromForm(document.getElementById("editUserForm")))
                    .then(() => {
                        document.location.reload();
                        flagEdit = 0;
                    })
        })
    }
})

//метод создания редактируемого пользователя
function editUserFromForm (form) {

    let roles = []
    for(let i = 0; i < form.elements.roles.length; i++) {
        if(form.elements.roles[i].selected) {
        role = {
            id: form.elements.roles[i].value, 
            role: "ROLE_".concat(form.elements.roles[i].textContent)
        }
        roles.push(role)
        }
    }

    user = {
        id: form.elements.id.value,
        username: form.elements.username.value,
        lastName: form.elements.lastName.value,
        age: form.elements.age.value,
        email: form.elements.email.value,
        password: form.elements.password.value,
        roles: roles
    }
    return user;
}

// метод выполнения put запроса
async function putData(url, user) {
    
    const response = await fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    })
}

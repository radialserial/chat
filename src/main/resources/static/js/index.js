const jwt = "jwt" // TODO recuperar jwt de cookie ou algum estado automático
const socket = new WebSocket('ws://127.0.0.1:8081/conect?jwt=' + jwt); // TODO Colocar link correto do servidor de chat
const Client = Stomp.over(socket);


function openPopup() {
    const popup = document.getElementById("popup");
    popup.classList.remove("hidden");
}

function openChat() {
    const popup = document.getElementById("popup");
    const chatContainer = document.getElementById("chatContainer");
    const usernameInput = document.getElementById("usernameInput").value;

    if (usernameInput !== "") {
        popup.style.display = "none";
        chatContainer.classList.remove("hidden");
        sessionStorage.setItem("user", usernameInput);
    } else {
        alert("Digite um nome válido!");
    }
}

function sendMessage(e) {
    e.preventDefault();
    const messageInput = document.getElementById("messageInput").value;

    const message = {
        user: sessionStorage.getItem("user"),
        message: messageInput
    };

    Client.send("/app/chatMessage", {}, JSON.stringify(message));

    document.getElementById("messageInput").value = "";
}

function displayMessage(message, name) {
    const chatMessages = document.getElementById("chatMessages");
    const messageElement = document.createElement("div");

    messageElement.textContent = name + ": " + message;

    // Define os estilos diretamente
    messageElement.style.backgroundColor = "#f0f0f0";
    messageElement.style.border = "1px solid #ccc";
    messageElement.style.borderRadius = "5px";
    messageElement.style.padding = "10px";
    messageElement.style.marginBottom = "5px";

    chatMessages.appendChild(messageElement);
}

function connect(){
    Client.connect({}, function (frame) {
        console.log('Conectado: ' + frame);


        Client.subscribe('/chat', function (message) {
            const chatMessage = JSON.parse(message.body);
            displayMessage(chatMessage.message, chatMessage.user);
        });
    });
}


connect();
openPopup();

// Try to set up WebSocket connection with the handshake at "http://localhost:8080/stomp"
let sock = new SockJS("gs-guide-websocket");

// Create a new StompClient object with the WebSocket endpoint
let client = Stomp.over(sock);

// Start the STOMP communications, provide a callback for when the CONNECT frame arrives.
client.connect({}, frame => {
    // Subscribe to "/topic/messages". Whenever a message arrives add the text in a list-item element in the unordered list.
    client.subscribe("/topic/greetings", payload => {
        console.warn('****************************');
        console.log(payload);

    });

});

// Take the value in the ‘message-input’ text field and send it to the server with empty headers.
function sendMessage(){

    let input = document.getElementById("message-input");
    let message = input.value;
    
    client.send('/app/hello', {}, JSON.stringify({'from':'aa','text':message}));

}
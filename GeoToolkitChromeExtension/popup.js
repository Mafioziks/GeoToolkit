var port = null;

document.addEventListener('DOMContentLoaded', function() {

});

chrome.runtime.onConnect.addListener(function(extPort) {
  console.assert(extPort.name == "sendertogps");
  extPort.onMessage.addListener(function(msg) {
    if (msg.status == "close"){
      console.log("Received - close");
      return;
    }
    else if (msg.status == "upload"){
      console.log("Received - send to gps");
      console.log(msg);
      if (port == null) {
        connect();
      }
      if (port != null) {
        console.log('Sending msg');
        port.postMessage(msg);
        port.postMessage({action: "File End"});
        console.log('Message sent to GPX');
      }
      extPort.postMessage({status: "success"});
    }
  });
});

function appendMessage(text) {
  document.getElementById('response').innerHTML += "<p>" + text + "</p>";
}

function updateUiState() {
  if (port) {
    document.getElementById('connect-button').style.display = 'none';
    document.getElementById('input-text').style.display = 'block';
    document.getElementById('send-message-button').style.display = 'block';
  } else {
    document.getElementById('connect-button').style.display = 'block';
    document.getElementById('input-text').style.display = 'none';
    document.getElementById('send-message-button').style.display = 'none';
  }
}

function sendNativeMessage() {
  message = document.getElementById('input-text').value;
  port.postMessage(message);
  // message = {"text": document.getElementById('input-text').value};
  // port.postMessage(message);
  appendMessage("Sent message: <b>" + message + "</b>");
}

function onNativeMessage(message) {
  appendMessage("Received message: <b>" + JSON.stringify(message) + "</b>");
}

function onDisconnected() {
  port = null;
  updateUiState()
  console.log('App disconnected');
}

function connect(env = 'app') {
  var hostName = "lv.id.tomsteteris.geotoolkit";
  port = chrome.runtime.connectNative(hostName);
  console.log('Connecting to app');
  if (env == 'ui') {
    port.onMessage.addListener(onNativeMessage);
    port.onDisconnect.addListener(onDisconnected);
    updateUiState();
  }
}

document.addEventListener('DOMContentLoaded', function () {
  document.getElementById('connect-button').addEventListener(
      'click', connect('ui'));
  document.getElementById('send-message-button').addEventListener(
      'click', sendNativeMessage);
  updateUiState();
});

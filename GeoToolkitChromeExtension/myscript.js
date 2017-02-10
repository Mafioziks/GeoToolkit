setTimeout(showGPX(), 100);

function showGPX() {
    var gpxFile = document.getElementById("dataString");

    if (gpxFile == null) {
        setTimeout(showGPX(), 100);
    } else {
        var port = chrome.runtime.connect({name: "sendertogps"});
        port.postMessage(
            {
                device: "garmin",
                gpxFile: gpxFile.innerHTML,
                status: "upload"
            }
        );
        console.log("Message sent");
        port.onMessage.addListener(function(msg) {
            console.log("Listener Added");
            var counter = 0;
            if (msg.status == "success"){
                console.log("MSG Success");
                port.postMessage({status: "close"});
                var place = document.getElementById("garminDisplay").innerHTML = "SUCCESS";
            } else if (msg.status == "false"){
                console.log("MSG FAIL");
                if (counter > 3)
                    port.postMessage({status: "close"});
                else {
                    counter++;
                    port.postMessage(
                        {
                            device: "garmin",
                            gpxFile: gpxFile.innerHTML,
                            status: "upload"
                        }
                    );
                }
            }
        });
        // alert(gpxFile.innerHTML);
    }
}
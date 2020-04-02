const { app, BrowserWindow } = require("electron");
const path = require("path");

let mainWindow;

let blueTheme = "body {color: #FF0000;}";

function createWindow() {

	mainWindow = new BrowserWindow({
		width: 800,
		height: 600,
		webPreferences: {
			nodeIntegration: false,
			preload: path.join(__dirname, "preload.js")
    	}
	});

	mainWindow.loadFile("src/SplashScreen/splashscreen.html");
	mainWindow.webContents.openDevTools();

	mainWindow.addListener("test", function testanswer(e) {
		mainWindow.dispatchEvent(new CustomEvent("test-answer", {"detail": "this is an answer"}));
		console.log("test main");
	});
	
	console.log(mainWindow.webContents);

}

app.whenReady().then(createWindow);

app.on("window-all-closed", function() {
	if (process.platform !== "darwin")
		app.quit();
});

app.on("activate", function() {
	if (BrowserWindow.getAllWindows().length === 0)
		createWindow();
});
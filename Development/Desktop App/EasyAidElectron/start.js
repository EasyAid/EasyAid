const electron = require('electron')
const BrowserWindow = electron.BrowserWindow
const app = electron.app

let SplashScreen = null

function CreateSplashScreen() {
	SplashScreen = new BrowserWindow({
		width: 800,
		height: 400,
		frame: false,
		resizable: false,
		show: false,
		backgroundColor: '#00796A'
	})
	
	SplashScreen.loadFile('src/SplashScreen.html')

	SplashScreen.webContents.openDevTools()

	SplashScreen.once('ready-to-show', () => {
        SplashScreen.show()
        SplashScreen.on('closed', () => {
            SplashScreen = null
        })
    })
}

app.on('ready', () => {
    CreateSplashScreen()
})

app.on('window-all-closed', () => {
	if (process.platform !== 'darwin') {
		app.quit()
	}
})

app.on('activate', () => {
	if (win === null) {
		CreateSplashScreen()
	}
})

function test() {
	console.log("test")
}
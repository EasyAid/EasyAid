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
		backgroundColor: '#00796A',
		webPreferences: {
			devTools: false
		}
	})
	
	SplashScreen.loadFile('src/SplashScreen.html')

	SplashScreen.once('ready-to-show', () => {
        SplashScreen.show()
        SplashScreen.on('closed', () => {
            SplashScreen = null
        })
    })
}

function CreateFirstWindow() {
	win = new BrowserWindow({
		width: 1000,
		height: 600,
		frame: false,
		resizable: false,
		show: false,
		/*webPreferences: {
			devTools: false
		}*/
	})
	
	//win.loadFile('src/SelectAccount.html')
	//win.loadFile('src/DoctorLogin.html')
	win.loadFile('src/PatientLogin.html')

	win.once('ready-to-show', () => {
        win.show()
        win.on('closed', () => {
            win = null
        })
    })
}


app.on('ready', () => {
	//CreateSplashScreen()
	CreateFirstWindow()
})

app.on('window-all-closed', () => {
	if (process.platform !== 'darwin') {
		app.quit()
	}
})

app.on('activate', () => {
	if (win === null) {
		//CreateSplashScreen()
		CreateFirstWindow()
	}
})

function test() {
	console.log("test")
}
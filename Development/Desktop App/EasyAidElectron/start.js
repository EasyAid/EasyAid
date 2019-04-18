const electron = require('electron')
const BrowserWindow = electron.BrowserWindow
const app = electron.app
const session = electron.session;

let SplashScreen = null

global.PatientShared = {
	type: "patient",
	id: "null",
	nome: "null",
	cognome: "null",
	dataNascita: "00-00-0000",
	codiceFiscale: "null",
	sesso: "null",
	provinciaNascita: "null",
	cittaNascita: "null",
	provinciaResidenza: "null",
	cittaResidenza: "null",
	viaResidenza: "null",
	password: "null"
};

function CreateSplashScreen() {
	SplashScreen = new BrowserWindow({
		width: 800,
		height: 400,
		frame: false,
		resizable: false,
		show: false,
		backgroundColor: '#00796A',
		icon: 'img/Icon.png'
		/*webPreferences: {
			devTools: false
		}*/
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
		icon: 'img/Icon.png'
		/*webPreferences: {
			devTools: false
		}*/
	})
	
	//win.loadFile('src/SelectAccount.html')
	//win.loadFile('src/Doctor/DoctorLogin.html')
	win.loadFile('src/Patient/PatientLogin.html')
	//win.loadFile('src/Pharmacy/PharmacyLogin.html')
	//win.loadFile('src/Patient/PatientRegistration.html')
	//win.loadFile('src/Patient/PatientHome.html')

	win.once('ready-to-show', () => {
        win.show()
        win.on('closed', () => {
            win = null
        })
    })
}


app.on('ready', () => {
	CreateSplashScreen()
	//CreateFirstWindow()
})

app.on('window-all-closed', () => {
	if (process.platform !== 'darwin') {
		//session.defaultSession.clearStorageData({storages: "cookie"});
		app.quit()
	}
})

app.on('activate', () => {
	if (win === null) {
		CreateSplashScreen()
		//CreateFirstWindow()
	}
})

function test() {
	console.log("test")
}
const { app, BrowserWindow } = require('electron')

let SplashScreen = null
let Empty = null

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

	SplashScreen.once('ready-to-show', () => {
        SplashScreen.show()
        SplashScreen.on('closed', () => {
            SplashScreen = null
        })
    })
}

function CreateEmpty() {
	Empty = new BrowserWindow({ 
        parent: SplashScreen, 
        modal: true, 
        show: false,
        width: 1280,
        height: 720
	})
	
	Empty.loadFile('src/empty.html')
	
    Empty.once('ready-to-show', () => {
        Empty.show()
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
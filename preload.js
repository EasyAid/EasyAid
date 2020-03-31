// All of the Node.js APIs are available in the preload process.
// It has the same sandbox as a Chrome extension.
/*window.addEventListener('DOMContentLoaded', () => {
  const replaceText = (selector, text) => {
    const element = document.getElementById(selector)
    if (element) element.innerText = text
  }

  for (const type of ['chrome', 'node', 'electron']) {
    replaceText(`${type}-version`, process.versions[type])
  }
})*/
const { ipcRenderer } = require('electron');

function init() {
	// add global variables to your web page
	
	ipcRenderer.send("theme-request", "");
	ipcRenderer.on("themes", (event, msg) => console.log("msg"));
	window.isElectron = true;
	console.log("preload");
}

init();

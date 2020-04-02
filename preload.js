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
const { remote } = require('electron');

function preload() {
	console.log(window);
	window.addEventListener("test-answer", function(e) {
		console.log(e.details);
	});
	window.dispatchEvent(new CustomEvent("test", {"details": "test question"}));
}

preload();
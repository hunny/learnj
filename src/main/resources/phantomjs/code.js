var system = require('system')
var address = system.args[1];
var shot = system.args[2];
shot = (shot == undefined ? 'defaultName' : shot);
var page = require('webpage').create();
page.settings.userAgent = 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36';
var url = address;
page.onError = function(msg, trace) {
	var msgStack = ['ERROR: ' + msg];
	if (trace && trace.length) {
		msgStack.push('TRACE:');
		trace.forEach(function(t) {
			msgStack.push(' -> ' + t.file + ': ' + t.line + (t.function ? ' (in function "' + t.function +'")' : ''));
		});
	}
	console.error(msgStack.join('\n'));
	//phantom.exit();
};
page.onAlert = function() {
	//console.log('onAlert');
};
page.onCallback = function() {
	//console.log('onCallback');
};
page.onClosing = function() {
	//console.log('onClosing');
}; 
page.onConfirm = function() {
	//console.log('onConfirm');
}; 
page.onConsoleMessage = function() {
	//console.log('onConsoleMessage');
};
page.onFilePicker = function() {
	//console.log('onFilePicker');
};
page.onInitialized = function() {
	//console.log('onInitialized');
};
page.onLoadFinished = function() {
	//console.log('onLoadFinished');
};
page.onLoadStarted = function() {
	//console.log('onLoadStarted');
};
page.onNavigationRequested = function() {
	//console.log('onNavigationRequested');
};
page.onPageCreated = function() {
	//console.log('onPageCreated');
};
page.onPrompt = function() {
	//console.log('onPrompt');
};
page.onResourceError = function(resourceError) {
  //console.log('Unable to load resource (#' + resourceError.id + 'URL:' + resourceError.url + ')');
  //console.log('Error code: ' + resourceError.errorCode + '. Description: ' + resourceError.errorString);
};
page.onResourceReceived = function(response) {
	//console.log('Response (#' + response.id + ', stage "' + response.stage + '"): ' + JSON.stringify(response));
};
page.onResourceRequested = function(requestData, networkRequest) {
  //console.log('Request (#' + requestData.id + '): ' + JSON.stringify(requestData));
};
page.onResourceTimeout = function() {
	//console.log('onResourceTimeout');
};
page.onUrlChanged = function() {
	//console.log('onUrlChanged');
};
page.viewportSize = {
	width: 1024,
	height: 800
};
page.open(url, function(status) {
	if (status !== 'success') {
		console.log('Unable to request ' + url);
		phantom.exit();
	} else {
		window.setTimeout(function() {
			page.render(shot + ".png"); // 截图
			console.log(page.content);
			phantom.exit();
		}, 5000);
	}
});

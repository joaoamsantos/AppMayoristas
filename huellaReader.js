// Empty constructor
function HuellaReader() {}

// The function that passes work along to native shells
// Message is a string, duration may be 'long' or 'short'
HuellaReader.prototype.show = function(message, duration, successCallback, errorCallback) {
  var options = {};
  options.message = message;
  options.duration = duration;
  cordova.exec(successCallback, errorCallback, 'HuellaReader', 'show', [options]);
}

// Installation constructor that binds ToastyPlugin to window
HuellaReader.install = function() {
  if (!window.plugins) {
    window.plugins = {};
  }
  window.plugins.huellaReader = new HuellaReader();
  return window.plugins.HuellaReader;
};
cordova.addConstructor(HuellaReader.install);

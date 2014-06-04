var ViewModel = function() {
    var self = this;

    self.temperature = ko.observable();
    self.humidity = ko.observable();

    self.setConditions = function(temperature, humidity) {
        self.temperature(temperature);
        self.humidity(humidity);
    };
};

var _viewModel = new ViewModel();

_viewModel.temperature.subscribe(function(newValue) {
    $('#temperature').thermometer(
            {percent: newValue, orientation: 'vertical', animate: false});
});

_viewModel.humidity.subscribe(function(newValue) {
    $('#humidity').thermometer(
            {percent: newValue, orientation: 'vertical', animate: false});
});

ko.applyBindings(_viewModel);

_viewModel.setConditions(70, 80);
		
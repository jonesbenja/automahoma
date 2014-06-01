function ViewModel() {
    var self = this;
    
    self.future_conditions = ko.observableArray([]);
    self.sample_time = ko.observable();
    self.temperature = ko.observable();
    self.humidity = ko.observable();
    self.precipitation = ko.observable();
        
    self.update = function(data) {
        self.future_conditions(data.future_conditions);
        self.sample_time(data.current_conditions.sample_time);
        self.temperature(data.current_conditions.temperature);
        self.humidity(data.current_conditions.humidity);
        self.precipitation(data.current_conditions.precipitation);
    };
    
};

var viewModel = new ViewModel();

ko.applyBindings(viewModel);

(function weatherUpdateTask() {
  $.ajax({
    url: "/forecast", 
    success: function(data) {
      viewModel.update(data);
    },
    complete: function() {
      setTimeout(weatherUpdateTask, 15000);
    }
  });
})();

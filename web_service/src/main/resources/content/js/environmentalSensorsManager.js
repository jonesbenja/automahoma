function ViewModel() {
    var self = this;
    
    self.environmentalSensors = ko.observableArray([]);
      
    self.update = function(data) {
        self.environmentalSensors(data);
    };
    
};

var viewModel = new ViewModel();

ko.applyBindings(viewModel);

(function environmentalSensorsUpdateTask() {
  $.ajax({
    url: "/environmentalSensors", 
    success: function(data) {
      viewModel.update(data);
    },
    complete: function() {
      setTimeout(environmentalSensorsUpdateTask, 15000);
    }
  });
})();

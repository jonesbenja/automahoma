function ViewModel() {
    var self = this;
    
    self.contactSensors = ko.observableArray([]);
      
    self.update = function(data) {
        self.contactSensors(data);
    };
    
};

var viewModel = new ViewModel();

ko.applyBindings(viewModel);

(function contactSensorsUpdateTask() {
  $.ajax({
    url: "/contactSensors", 
    success: function(data) {
      viewModel.update(data);
    },
    complete: function() {
      setTimeout(contactSensorsUpdateTask, 15000);
    }
  });
})();

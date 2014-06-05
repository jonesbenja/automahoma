function ViewModel() {
    var self = this;
    
    self.actuators = ko.observableArray([]);
      
    self.update = function(data) {
        self.actuators(data);
    };
    
};

var viewModel = new ViewModel();

ko.applyBindings(viewModel);

function getActuators() {
  $.ajax({
    url: "/actuators", 
    success: function(data) {
        viewModel.update(data);
    },
    error : function(jqXHR, textStatus, errorThrown) {
        alert(errorThrown);
    }
  });
};

function sendActuators() {
    var putStr = ko.toJSON(viewModel.actuators);

    $.ajax({
        type : "PUT",
        url: "/actuators",
        contentType: "application/json",
        data : putStr,
        error : function(jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}

getActuators();

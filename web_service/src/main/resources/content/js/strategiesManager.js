var STRATEGIES_URL = "/strategies";

function StrategiesModel(strategy) {
    return {
        name : strategy.name,
        active : ko.observable(strategy.active),
        parameters : strategy.parameters
    };
}

function ViewModel() {
    var self = this;

    self.strategies = new ko.observableArray();
  
    self.update = function(data) {
        self.strategies.removeAll();
 
        for (var i = 0; i < data.length; i++) {
            self.strategies.push(new StrategiesModel(data[i]));
        }
    };
    
    self.apply = function() {
        sendProfiles();
    };
};

function sendProfiles() {
    var putStr = ko.toJSON(viewModel.strategies);

    $.ajax({
        type : "PUT",
        url : STRATEGIES_URL,
        contentType: "application/json",
        data : putStr,
        error : function(jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
};
  
function getStrategies() {
    $.ajax({
        type : "GET",
        url : STRATEGIES_URL,
        success : function(data) {
            viewModel.update(data);
        },
        error : function(jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
};

var viewModel = new ViewModel();

ko.applyBindings(viewModel);

getStrategies();

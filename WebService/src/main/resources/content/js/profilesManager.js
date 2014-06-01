var PROFILES_URL = "/profiles";

function ProfileModel(checked, name, minimumTemperature, maximumTemperature) {
    return {
        checked : new ko.observable(checked),
        name : name,
        minimum_temperature : minimumTemperature,
        maximum_temperature : maximumTemperature
    };
}

function ViewModel() {
    var self = this;

    self.profiles = new ko.observableArray();
  
    self.update = function(data) {
        self.profiles.removeAll();
 
        for (var i = 0; i < data.length; i++) {
            self.profiles.push(new ProfileModel(false, data[i].name, 
                    data[i].minimum_temperature, data[i].maximum_temperature));
        }
    };
};

function sendProfiles() {
    var putStr = ko.toJSON(viewModel.profiles);

    $.ajax({
        type : "PUT",
        url : PROFILES_URL,
        contentType: "application/json",
        data : putStr,
        error : function(jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
};
  
function getProfiles() {
    $.ajax({
        type : "GET",
        url : PROFILES_URL,
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

getProfiles();

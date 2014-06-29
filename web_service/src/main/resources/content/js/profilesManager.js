var PROFILES_URL = "/profiles";
var CALENDAR_URL = "/calendar";

function ProfileModel(checked, name, minimumTemperature, maximumTemperature) {
    return {
        checked : new ko.observable(checked),
        name : name,
        minimum_temperature : minimumTemperature,
        maximum_temperature : maximumTemperature
    };
}

function CalendarModel(profile, startTime, endTime) {
    return {
        profile : profile,
        startTime : ko.observable(startTime),
        endTime : ko.observable(endTime),
        selected : ko.observable(false)
    };
}

function ViewModel() {
    var self = this;
    
    self.hoursOfDay = [];
    
    for (var i = 0; i < 24; i++) {
        self.hoursOfDay.push(i);
    }

    self.profileNames = ko.observableArray();
    self.profiles = ko.observableArray();
    self.dayNames = [ "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
    self.weekDays = ko.observableArray(
        [ 
            { dayName : "Mon", entries : ko.observableArray([ ]) }, 
            { dayName : "Tue", entries : ko.observableArray([ ]) }, 
            { dayName : "Wed", entries : ko.observableArray([ ]) }, 
            { dayName : "Thu", entries : ko.observableArray([ ]) }, 
            { dayName : "Fri", entries : ko.observableArray([ ]) }, 
            { dayName : "Sat", entries : ko.observableArray([ ]) }, 
            { dayName : "Sun", entries : ko.observableArray([ ]) } 
        ]
    );
    
    self.selectedProfileName = ko.observable();
    self.selectedDay = ko.observable();
    self.selectedStartTime = ko.observable();
    self.selectedEndTime = ko.observable();
    
    self.setProfiles = function(data) {
        self.profiles.removeAll();
        self.profileNames.removeAll();
 
        for (var i = 0; i < data.length; i++) {
            self.profiles.push(new ProfileModel(false, data[i].name, 
                    data[i].minimum_temperature, data[i].maximum_temperature));
                    
            self.profileNames.push(data[i].name);
        }
    };
    
    self.setCalendar = function(data) {
        
        ko.utils.arrayForEach(self.weekDays(), function(weekDay) {
            weekDay.entries.removeAll();
        
            for (var i = 0; i < data.length; i++) {
                var day = data[i];
                if (day.dayName === weekDay.dayName) {
                    for (var j = 0; j < day.entries.length; j++) {
                        var entry = day.entries[j];
                    
                        var dateModel = new CalendarModel(
                                entry.profile, entry.startTime, entry.endTime);
            
                        weekDay.entries.push(dateModel);
                    }
                }
            }
        });
    };
    
    self.updateCalendar = function() {
        ko.utils.arrayForEach(self.weekDays(), function(weekDay) {
            if (weekDay.dayName === self.selectedDay()) {
                weekDay.entries.push(new CalendarModel(self.selectedProfileName(), 
                        self.selectedStartTime(), self.selectedEndTime()));
            }
        });
        sendCalendar();
    };
    
    self.removeEntries = function() {
        ko.utils.arrayForEach(self.weekDays(), function(weekDay) {
            weekDay.entries.remove(function(entry) {
                return entry.selected();
            });
        });
        sendCalendar();
    };
};

function sendProfiles() {
    var putStr = ko.toJSON(viewModel.profiles);

    $.ajax({
        type : "PUT",
        url : PROFILES_URL,
        contentType : "application/json",
        data : putStr,
        error : function(jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
};

function sendCalendar() {
    var putStr = ko.toJSON(viewModel.weekDays);
    
    $.ajax({
       type : "PUT",
       url : CALENDAR_URL,
       contentType : "application/json",
       data : putStr,
       error : function(jqXHR, textStatus, errorThrown) {
           alert(errorThrown);
       }
    });
}
  
function getProfiles() {
    $.ajax({
        type : "GET",
        url : PROFILES_URL,
        success : function(data) {
            viewModel.setProfiles(data);
        },
        error : function(jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
};

function getCalendar() {
    $.ajax({
        type : "GET",
        url : CALENDAR_URL,
        success : function(data) {
            viewModel.setCalendar(data);
        },
        error : function(jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}

var viewModel = new ViewModel();

ko.applyBindings(viewModel);

getProfiles();
getCalendar();

(function () {
    var app = angular.module('home', [])
        .directive('recordsList', function () {
            return {
                restrict: 'EA',
                templateUrl: "../views/angular/records-list.html",
                scope: {},
                controller: function ($scope) {
                    $scope.average = 0;
                    var obj = window.accessor;
                    var records = JSON.parse(obj.getRecords());
                    var sum = 0;
                    var count = 0;
                    $.each(records,function(row){
                        var record = records[row];
                        if (record['gas']!=0){
                            var mpg = record['miles']/record['gas'];
                            record['mpg'] = mpg;
                            sum += mpg;
                            count++;
                        } else {
                            record['mpg'] = -1;
                        }
                        record['date'] =  record["recordMilesTime"];
                        delete record["recordMilesTime"];
                        records[row] = record;
                    });
                    if (count!==0){
                        $scope.average = sum/count;
                    }
                    $scope.records = records;
                    $scope.recordsSortOrder = "-mpg";
                }
            };
        });
}());
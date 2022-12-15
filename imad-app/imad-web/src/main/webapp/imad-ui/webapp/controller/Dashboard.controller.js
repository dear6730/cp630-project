sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel"
],
/**
 * @param {typeof sap.ui.core.mvc.Controller} Controller
 */
function (Controller, JSONModel) {
    "use strict";

    return Controller.extend("ec.laurier.imad.controller.Dashboard", {
        onInit: function () {
            this.populateTotalStockValue();
            this.populateTotalStockValueByCategory();
            this.populateTop5ProductsSold();
            this.populateOverviewStockingIssues();
            this.populateCurrentStateOfStock();
            this.populateProductsOutOfStockOrNearlyOutOfStock();
        },

        populateTotalStockValue: function() {
            var oCard = this.getView().byId("totalStockValue");
            var oModel = this.getView().getModel("cardModel");
            var oCardData = oModel.getProperty("/totalStockValue");
            var oTitle;
            var oList = [];
            var oMeasures = [];

            // call REST-API
            $.when(
                $.ajax({
                    url: "/imad-rs/rest/card1Title",
                    dataType: "json",
                    success: function(result) {
                        oTitle = result.results;
                    }
                }),
                $.ajax({
                    url: "/imad-rs/rest/card1List",
                    dataType: "json",
                    success: function(result) {
                        oList = result.results;
                    }
                }),
                $.ajax({
                    url: "/imad-rs/rest/card1Measures",
                    dataType: "json",
                    success: function(result) {
                        oMeasures = result.results;
                    }
                })
            ).then(function(){
                // assign new value
                if(oTitle !== null && oList.length > 0 && oMeasures.length > 0) {
                    oCardData["sap.card"].header.title = "Total Stock value";
                    oCardData["sap.card"].header.details = oTitle.details;
                    oCardData["sap.card"].header.data.json = oTitle;
                    oCardData["sap.card"].content.data.json.list = oList;
                    oCardData["sap.card"].content.measures = oMeasures;
                    oModel.setProperty("/totalStockValue",oCardData);
                    oCard.refresh();
                }
            });
        },

        populateTotalStockValueByCategory: function() {
            var oCard = this.getView().byId("totalStockValueByCategory");
            var oModel = this.getView().getModel("cardModel");
            var oCardData = oModel.getProperty("/totalStockValueByCategory");
            var oMeasures = [];
            
            // call REST-API

            $.ajax({
                url: "/imad-rs/rest/card2",
                dataType: "json",
                success: function(result) {
                    oMeasures = result.results;
                    // assign new value
                    if(oMeasures.length > 0) {
                        oCardData["sap.card"].header.title = "Total Stock by Category";
                        oCardData["sap.card"].content.data.json.measures = oMeasures;
                        oModel.setProperty("/totalStockValueByCategory", oCardData);
                        oCard.refresh();
                    }
                }
            });

        },

        populateTop5ProductsSold: function() {

            var oCard = this.getView().byId("top5ProductsSold");
            var oModel = this.getView().getModel("cardModel");
            var oCardData = oModel.getProperty("/top5ProductsSold");
            
            var oResults = [];

            // MOCK
            var newList = [
                {
                    "Product": "Product 5 - Blue",
                    "Stock Value": "9000"
                },
                {
                    "Product": "Product 17",
                    "Stock Value": "4050"
                },
                {
                    "Product": "Product 12",
                    "Stock Value": '800'
                },
                {
                    "Product": "Product 5 - Yellow",
                    "Stock Value": 750
                },
                {
                    "Product": "Product 3",
                    "Stock Value": 560
                }
            ];

            oCardData["sap.card"].content.data.json.list = newList;
            oModel.setProperty("/top5ProductsSold", oCardData);

        },

        populateOverviewStockingIssues: function() {
            var oCard = this.getView().byId("overviewStockingIssues");
            var oModel = this.getView().getModel("cardModel");
            var oCardData = oModel.getProperty("/overviewStockingIssues");

            var oResults = [];

            // Mock

            // var oosPercent = "26";  // out of stock
            // var noosPercent = "11"; // nearly out of stock

            // oCardData["sap.card"].content.body[1]["inlines"][0]["text"] = oosPercent + "%";
            // oCardData["sap.card"].content.body[4]["inlines"][0]["text"] = noosPercent + "%";
            // oModel.setProperty("/overviewStockingIssues",oCardData);

            // call REST API
            $.ajax({
                url: "/imad-rs/rest/card4",
                dataType: "json",
                success: function(result) {
                    oResults = result.results;

                    // assign new value
                    if(oResults.length > 0) {

                        var oosPercent = oResults[0]["percentageOutOfStock"];
                        var noosPercent = oResults[0]["percentageNearlyOutOfStock"];

                        oCardData["sap.card"].content.body[1]["inlines"][0]["text"] = oosPercent + "%";
                        oCardData["sap.card"].content.body[4]["inlines"][0]["text"] = noosPercent + "%";

                        oModel.setProperty("/overviewStockingIssues", oCardData);
                        oCard.refresh();
                    }
                }
            });
        },

        populateCurrentStateOfStock: function() {
            var oCard = this.getView().byId("currentStateOfStock");
            var oModel = this.getView().getModel("cardModel");
            var oCardData = oModel.getProperty("/currentStateOfStock");
            
            var oResults = [];

            // call REST-API
            $.ajax({
                url: "/imad-rs/rest/card5",
                dataType: "json",
                success: function(result) {
                    oResults = result.results;
                    if(oResults.length > 0) {
                        // assign new value
                        oCardData["sap.card"].header.title = "Current State of Stock";
                        oCardData["sap.card"].header.subTitle = "December 14, 2022";
                        oCardData["sap.card"].data.json.results = oResults;

                        oModel.setProperty("/currentStateOfStock", oCardData);
                        oCard.refresh();
                    }
                }
            });
        },
        
        populateProductsOutOfStockOrNearlyOutOfStock: function() {
            var oCard = this.getView().byId("productsOutOfStockOrNearlyOutOfStock");
            var oModel = this.getView().getModel("cardModel");
            var oCardData = oModel.getProperty("/productsOutOfStockOrNearlyOutOfStock");
            
            var oResults = [];


            // Mock

            // locating the data elements
/*            
            console.log("header percent number:" + oCardData["sap.card"].header.data.json.number);
            console.log("header trend:" + oCardData["sap.card"].header.data.json.trend);
            console.log("header state:" + oCardData["sap.card"].header.data.json.state);
            console.log("header target number:" + oCardData["sap.card"].header.data.json.target.number);
            console.log("header details:" + oCardData["sap.card"].header.data.json.details);

            console.log("data list...:" + oCardData["sap.card"].content.data.json.list);
            console.log("data list length...:" + oCardData["sap.card"].content.data.json.list.length);

            //var len = oCardData["sap.card"].content.data.json.list.length;

            console.log("?? = " + oCardData["sap.card"].content.data.json.list[0]);

            for (var key in oCardData["sap.card"].content.data.json.list) {
                console.log("Key: " + key);
                console.log("Value: " + oCardData["sap.card"].content.data.json.list[key]);

                for(var key2 in oCardData["sap.card"].content.data.json.list[key]){
                    console.log("Key2: " + key2);
                    console.log("Value: " + oCardData["sap.card"].content.data.json.list[key][key2]);
                }
            }
   */         

            //console.log("Test 1: " + oCardData["sap.card"].content.data.json.list[0]["Month"]);
            //console.log("Test 2: " + oCardData["sap.card"].content.data.json.list[0]["Stock"]);

/*
            oCardData["sap.card"].content.data.json.list[0]["Month"] = "Q3 2021";
            oCardData["sap.card"].content.data.json.list[0]["Stock"] = "11.26";

            oCardData["sap.card"].content.data.json.list[1]["Month"] = "Q4 2021";
            oCardData["sap.card"].content.data.json.list[1]["Stock"] = "26.11";

            oCardData["sap.card"].content.data.json.list[2]["Month"] = "Q1 2022";
            oCardData["sap.card"].content.data.json.list[2]["Stock"] = "24.1";

            oCardData["sap.card"].content.data.json.list[3]["Month"] = "Q2 2022";
            oCardData["sap.card"].content.data.json.list[3]["Stock"] = "12.21";

            oCardData["sap.card"].content.data.json.list[4]["Month"] = "Q3 2022";
            oCardData["sap.card"].content.data.json.list[4]["Stock"] = "21.26";

            oCardData["sap.card"].content.data.json.list[5]["Month"] = undefined;
            oCardData["sap.card"].content.data.json.list[5]["Stock"] = undefined;
*/

            oCardData["sap.card"].header.data.json.number = "9.9";
            oCardData["sap.card"].header.data.json.trend = "Down";
            oCardData["sap.card"].header.data.json.state = "Good";
            oCardData["sap.card"].header.data.json.details = "Q4 2022 (predicted)";

            oCardData["sap.card"].header.data.json.target.number = "10.0";

            oCardData["sap.card"].content.data.json.list = [
                { "Month": "Q3 2021", "Stock": "11.26" },
                { "Month": "Q4 2021", "Stock": "26.11" },
                { "Month": "Q1 2022", "Stock": "24.1" },
                { "Month": "Q2 2022", "Stock": "12.21" },
                { "Month": "Q3 2022", "Stock": "21.26" }
            ];

            oModel.setProperty("/productsOutOfStockOrNearlyOutOfStock", oCardData);
        }
                
    });
});
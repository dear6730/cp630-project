sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/core/format/NumberFormat",
    "sap/ui/core/format/DateFormat"
],
/**
 * @param {typeof sap.ui.core.mvc.Controller} Controller
 */
function (Controller, NumberFormat, DateFormat) {
    "use strict";

    var oFormatOptions = {
        style: "short",
        decimals: 1,
        shortDecimals: 2
    };
    var oFloatFormat = NumberFormat.getFloatInstance(oFormatOptions);
    var oDateFormat = DateFormat.getDateInstance({pattern: "MMM d, y"});

    return Controller.extend("ec.laurier.imad.controller.Dashboard", {
        onInit: function () {
            this.populateTotalStockValue();
            this.populateTotalStockValueByCategory();
            this.populateTop5ProductsSold();
            this.populateOverviewStockingIssues();
            this.populateCurrentStateOfStock();
            this.populateCombinedPercentageHistory();
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
                    url: "/imad-rs/rest/card1",
                    dataType: "json",
                    success: function(result) {
                        oTitle = result.results.header;
                        oList = result.results.list;
                        oMeasures =  result.results.measures;
                    }
                })
            ).then(function(){
                // assign new value
                if(oTitle !== null && oList.length > 0 && oMeasures.length > 0) {
                    //format total
                    oTitle.n = "$" + oFloatFormat.format(oTitle.n);
                    
                    oCardData["sap.card"].header.title = "Total Stock value";
                    oCardData["sap.card"].header.details = "as of " + oDateFormat.format(new Date(oTitle.details));
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

            // call REST-API
            $.ajax({
                url: "/imad-rs/rest/card3",
                dataType: "json",
                success: function(result) {
                    oResults = result.results;
                    // assign new values
                    if(oResults.length > 0) {
                        oCardData["sap.card"].header.title = "Stock Value of Top 5 Products";
                        oCardData["sap.card"].content.data.json.list = oResults;
                        oModel.setProperty("/top5ProductsSold", oCardData);
                        oCard.refresh();
                    }
                }
            });
        },

        populateOverviewStockingIssues: function() {
            var oCard = this.getView().byId("overviewStockingIssues");
            var oModel = this.getView().getModel("cardModel");
            var oCardData = oModel.getProperty("/overviewStockingIssues");

            var oResults = [];

            // call REST API
            $.ajax({
                url: "/imad-rs/rest/card4",
                dataType: "json",
                success: function(result) {
                    oResults = result.results;

                    // assign new values
                    if(oResults.length > 0) {

                        var oosPercentage = oResults[0]["outOfStockPercentage"];
                        var noosPercentage = oResults[0]["nearlyOutOfStockPercentage"];
                        var combinedPercentage = oResults[0]["combinedPercentage"];

                        oCardData["sap.card"].header.title = "Global Stocking Issues";
                        oCardData["sap.card"].content.body[1]["inlines"][0]["text"] = oosPercentage + "%";
                        oCardData["sap.card"].content.body[3]["inlines"][0]["text"] = noosPercentage + "%";
                        oCardData["sap.card"].content.body[6]["inlines"][0]["text"] = combinedPercentage + "%";

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
                        oCardData["sap.card"].header.title = "Products Out of Stock, or Nearly Out of Stock";
                        oCardData["sap.card"].header.subTitle = oDateFormat.format(new Date());
                        oCardData["sap.card"].data.json.results = oResults;

                        oModel.setProperty("/currentStateOfStock", oCardData);
                        oCard.refresh();
                    }
                }
            });
        },
        
        populateCombinedPercentageHistory: function() {
            var oCard = this.getView().byId("combinedPercentageHistory");
            var oModel = this.getView().getModel("cardModel");
            var oCardData = oModel.getProperty("/combinedPercentageHistory");

            var oHeader = [];
            var oResults = [];

            // call REST-API
            $.when(
                $.ajax({
                    url: "/imad-rs/rest/card6Header",
                    dataType: "json",
                    success: function(result) {
                        oHeader = result.results;
                    }
                }),
                $.ajax({
                    url: "/imad-rs/rest/card6",
                    dataType: "json",
                    success: function(result) {
                        oResults = result.results;
                    }
                })
            ).then(function(){
                // assign new values
                if(oHeader.length > 0 && oResults.length > 0) {

                    oCardData["sap.card"].header.title = "Historical Analysis of Global Combined Percentage";
                    oCardData["sap.card"].header.data.json["number"] = oHeader[0]["number"];
                    oCardData["sap.card"].header.data.json["trend"] = oHeader[0]["trend"];
                    oCardData["sap.card"].header.data.json["state"] = oHeader[0]["state"];

                    oCardData["sap.card"].content.data.json.list = oResults;
                    oModel.setProperty("/combinedPercentageHistory",oCardData);
                    oCard.refresh();
                }
            });
        }            
    });
});
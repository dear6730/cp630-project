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
            this.populateOverviewStockingIssues();
            this.populateCurrentStateOfStock();
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

        populateOverviewStockingIssues: function() {

            var oCard = this.getView().byId("overviewStockingIssues");
            var oModel = this.getView().getModel("cardModel");
            var oCardData = oModel.getProperty("/overviewStockingIssues");


            // Mock

            var oosPercent = "26";  // out of stock
            var noosPercent = "11"; // nearly out of stock

            // for (var key in oCardData["sap.card"].content.body[1]["inlines"][0]) {
            //     console.log("Key: " + key);
            //     console.log("Value: " + oCardData["sap.card"].content.body[1]["inlines"][0][key]);
            // }

            //console.log("Percentage?: " + oCardData["sap.card"].content.body[1]["inlines"][0]["text"]);

                oCardData["sap.card"].content.body[1]["inlines"][0]["text"] = oosPercent + "%";
                oCardData["sap.card"].content.body[4]["inlines"][0]["text"] = noosPercent + "%";
                oModel.setProperty("/overviewStockingIssues",oCardData);
            },

        populateCurrentStateOfStock: function() {
            var oCard = this.getView().byId("currentStateOfStock");
            var oModel = this.getView().getModel("cardModel");
            var oCardData = oModel.getProperty("/currentStateOfStock");
            
            var oResults = [];

            // call REST-API
            $.ajax({
                type: "GET",
                url: "/imad-rs/rest/card5",
                dataType: "json",
                crossDomain: false,
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
        }       
                
    });
});
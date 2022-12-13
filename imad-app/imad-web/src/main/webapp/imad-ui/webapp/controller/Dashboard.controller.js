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
                //this.populateTotalStockValue();
                this.populateTotalStockValueByCategory();
                this.populateCurrentStateOfStock();
            },

            populateTotalStockValue: function() {
                var oModel = this.getView().getModel("cardModel");
                var oCardData = oModel.getProperty("/totalStockValue");
                var oHeaderData = null;
                var oList = [];
                var oMeasures = [];

                // call REST-API
                // pending
                oHeaderData = {
                    "n": "$99,707",
                    "u": "CAD",
                    "trend": "Up",
                    "valueColor": "Critical"
                };
                oList = [{
                    "Category": "New Location 1",
                    "Category1": 999,
                    "Category2": 550,
                    "Category3": 300
                },{
                    "Category": "New Location 2",
                    "Category1": 1000,
                    "Category2": 550,
                    "Category3": 400
                },{
                    "Category": "New Location 3",
                    "Category1": 700,
                    "Category2": 156,
                    "Category3": 300
                }];
                oMeasures = [{
                        label: "New Category 1XX",
                        value: "{Category1}"
                    },{
                        label: "New Category 2XX",
                        value: "{Category2}"
                    },{
                        label: "New Category 3XX",
                        value: "{Category3}"
                    }];
                
                if(oHeaderData !== null && oList.length > 0 && oMeasures.length > 0) {
                    // assign new value
                    oCardData["sap.card"].header.title = "Total Stock value";
                    oCardData["sap.card"].header.details = "as of Dec 6, 2022 [NEW]";
                    oCardData["sap.card"].header.data.json = oHeaderData;
                    oCardData["sap.card"].content.data.json.list = oList;
                    oCardData["sap.card"].content.measures = oMeasures;
                    oModel.setProperty("/totalStockValue",oCardData);
                }
            },

            populateTotalStockValueByCategory: function() {
                var oCard = this.getView().byId("totalStockValueByCategory");
                var oModel = this.getView().getModel("cardModel");
                var oCardData = oModel.getProperty("/totalStockValueByCategory");
                var oMeasures = [];
                
                // call REST-API

                $.ajax({
                    type: "GET",
                    url: "/imad-rs/rest/card2",
                    dataType: "json",
                    crossDomain: false,
                    success: function(result) {
                        oMeasures = result.card2;
                        if(oMeasures.length > 0) {
                            // assign new value
                            oCardData["sap.card"].header.title = "Total Stock by Category";
                            oCardData["sap.card"].content.data.json.measures = oMeasures;
                            oModel.setProperty("/totalStockValueByCategory", oCardData);
                            oCard.refresh();
                        }
                    },
                    error: function(error) {
                        console.log("error : " + JSON.stringify(error)); 
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
                    type: "GET",
                    url: "/imad-rs/rest/card5",
                    dataType: "json",
                    crossDomain: false,
                    success: function(result) {
                        oResults = result.card5;
                        if(oResults.length > 0) {
                            // assign new value
                            oCardData["sap.card"].header.title = "Current State of Stock";
                            oCardData["sap.card"].header.subTitle = "December 13, 2022";
                            oCardData["sap.card"].data.json.results = oResults;

                            oModel.setProperty("/currentStateOfStock", oCardData);
                            oCard.refresh();

                        }
                    },
                    error: function(error) {
                        console.log("error : " + JSON.stringify(error)); 
                    }
                });
            }       
                 
        });
    });
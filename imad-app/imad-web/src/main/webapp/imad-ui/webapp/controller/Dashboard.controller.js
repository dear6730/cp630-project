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
                var cardManifests = new JSONModel();
                cardManifests.loadData("model/mockdata/cardsdata.json");
                this.getView().setModel(cardManifests, "manifests");
            }
        });
    });

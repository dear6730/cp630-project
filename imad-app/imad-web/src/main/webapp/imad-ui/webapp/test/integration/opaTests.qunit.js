/* global QUnit */
QUnit.config.autostart = false;

sap.ui.getCore().attachInit(function () {
	"use strict";

	sap.ui.require(["ec/laurier/imad/imadui/test/integration/AllJourneys"
	], function () {
		QUnit.start();
	});
});

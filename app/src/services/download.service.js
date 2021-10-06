import http from "./http.service";
import { Component } from "react";

export class DownloadService extends Component {
  static leadZero = x => (x < 10 ? "0" + x : x + "");
  static getReport(url, params, reportName, reportType, onSuccess) {
    http
      .get(url, {
        params: params,
        headers: {
          "Content-Type": "application/json"
        },
        responseType: "blob"
      })
      .then(res => {
        var contentType = "application/octet-stream";
        var urlCreator =
          window.URL || window.webkitURL || window.mozURL || window.msURL;
        if (urlCreator) {
          var blob = new Blob([res.data], { type: contentType });
          var url = urlCreator.createObjectURL(blob);
          var a = document.createElement("a");
          document.body.appendChild(a);
          a.style.display = "none";
          a.href = url;
          var d = new Date();
          var vv =
            d.getFullYear() +
            this.leadZero(d.getMonth() + 1) +
            this.leadZero(d.getDate()) +
            "_" +
            this.leadZero(d.getHours()) +
            this.leadZero(d.getMinutes());
          a.download = reportName + "-" + vv + "." + reportType;
          a.click();
          window.URL.revokeObjectURL(url);

          if (onSuccess) {
            onSuccess();
          }
        }
      });
  }
}

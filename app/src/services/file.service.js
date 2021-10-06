import http from "./httpService";
import { toast } from "react-toastify";
import { Component } from "react";

export class FileService extends Component {
  static uploadChargeFile(e) {
    toast.dismiss();
    let toastId = toast.info("فایل در حال بارگذاری میباشد ..", {
      autoClose: false,
      closeButton: false,
      closeOnClick: false
    });
    const data = new FormData();
    let file = e.target.files[0];
    data.append("file", file);
    return http
      .post("upload/charge", data)
      .then(({ data }) => {
        toast.success("بارگذاری فایل با موفقیت انجام شد.");
        return data;
      })
      .catch(() => {
        toast.error("خطا در بارگذاری فایل رخ داده است.", {
          autoClose: false
        });
      })
      .finally(() => {
        toast.dismiss(toastId);
      });
  }
}

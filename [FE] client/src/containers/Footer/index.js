import React, { Component } from 'react';
import './styles.css';
// import { assets } from '../../constants/assetsImage';
import { withTranslation } from 'react-i18next'
import brandpng from '../../img/brand.png'
import ghnpng from '../../img/ghn.png'
class Footer extends Component {

  render() {
    return (
      <>
     
          <section id="footer" className="bg-dark text-white">
      <div className="container">
        <div className="row">
          <div className="col-md-3 col-sm-6">
            <h1 >
              APum Store 
            </h1>
          <img src={brandpng}
            alt="Girl in a jacket" 
            layout="responsive" width="220" height="84" />
    
            <p className="mt-3">
                Nơi trải nghiệm sự thăng hoa của công nghệ .
            </p>
            <p>Bản quyền mới nhất năm @2022</p>
          </div>
          <div className="col-3">
            <p className="fs-3 fw-bolder">Thời gian mở cửa</p>
            <p>Thứ 2 đến thứ 7: 8h - 22h</p>
            <p>Chủ nhật: 9h - 23h</p>
            <div className="d-flex">
            
              <div
                className="rounded-circle d-flex align-items-center justify-content-center me-2"
                style={{
                  width: "20px",
                  height: "20px",
                  backgroundColor: "#3b5998",
                }}
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  className="icon icon-tabler icon-tabler-brand-facebook"
                  width={24}
                  height={24}
                  viewBox="0 0 24 24"
                  strokeWidth="1.5"
                  stroke="#ffffff"
                  fill="none"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                >
                  <path stroke="none" d="M0 0h24v24H0z" fill="none" />
                  <path d="M7 10v4h3v7h4v-7h3l1 -4h-4v-2a1 1 0 0 1 1 -1h3v-4h-3a5 5 0 0 0 -5 5v2h-3" />
                </svg>
              </div>
              <div
                className="rounded-circle d-flex align-items-center justify-content-center me-2"
                style={{
                  width: "40px",
                  height: "40px",
                  backgroundColor: "#00b2bf",
                }}
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  className="icon icon-tabler icon-tabler-brand-twitter"
                  width={24}
                  height={24}
                  viewBox="0 0 24 24"
                  strokeWidth="1.5"
                  stroke="#ffffff"
                  fill="none"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                >
                  <path stroke="none" d="M0 0h24v24H0z" fill="none" />
                  <path d="M22 4.01c-1 .49 -1.98 .689 -3 .99c-1.121 -1.265 -2.783 -1.335 -4.38 -.737s-2.643 2.06 -2.62 3.737v1c-3.245 .083 -6.135 -1.395 -8 -4c0 0 -4.182 7.433 4 11c-1.872 1.247 -3.739 2.088 -6 2c3.308 1.803 6.913 2.423 10.034 1.517c3.58 -1.04 6.522 -3.723 7.651 -7.742a13.84 13.84 0 0 0 .497 -3.753c-.002 -.249 1.51 -2.772 1.818 -4.013z" />
                </svg>
              </div>
              <div
                className="rounded-circle d-flex align-items-center justify-content-center me-2"
                style={{
                  width: "40px",
                  height: "40px",
                  backgroundColor: "#b2001f",
                }}
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  className="icon icon-tabler icon-tabler-brand-google"
                  width={24}
                  height={24}
                  viewBox="0 0 24 24"
                  strokeWidth="1.5"
                  stroke="#ffffff"
                  fill="none"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                >
                  <path stroke="none" d="M0 0h24v24H0z" fill="none" />
                  <path d="M17.788 5.108a9 9 0 1 0 3.212 6.892h-8" />
                </svg>
              </div>
              <div
                className="rounded-circle d-flex align-items-center justify-content-center"
                style={{
                  width: "40px",
                  height: "40px",
                  backgroundColor: "#426eb4",
                }}
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  className="icon icon-tabler icon-tabler-brand-instagram"
                  width={24}
                  height={24}
                  viewBox="0 0 24 24"
                  strokeWidth="1.5"
                  stroke="#ffffff"
                  fill="none"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                >
                  <path stroke="none" d="M0 0h24v24H0z" fill="none" />
                  <rect x={4} y={4} width={16} height={16} rx={4} />
                  <circle cx={12} cy={12} r={3} />
                  <line x1="16.5" y1="7.5" x2="16.5" y2="7.501" />
                </svg>
              </div>
             
            </div>
          
          </div>
          <div className="col-6">
            <p className="fs-3 fw-bolder">Liên hệ với chúng tôi</p>
            <p>CÔNG TY CỔ PHẦN ĐẦU TƯ VÀ DỊCH VỤ F&B VIỆT NAM</p>
            <p>Mã số thuế: 316657994</p>
            <p>Địa chỉ ĐKKD: 168 CMT8 P.1, Q.1 , TP HCM</p>
            <p>Phone: 1900 633 818</p>
            <p>Email: order.applepum@gmail.com</p>
            <img src={ghnpng}
            alt="Girl in a jacket" 
            layout="responsive" width="220" height="84" />
          </div>
        </div>
      </div>
    </section>
      </>
    );
  }
}

export default withTranslation()(Footer);
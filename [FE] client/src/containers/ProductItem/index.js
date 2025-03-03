import React, { Component } from "react";
import { connect } from "react-redux";
import { Link, withRouter } from "react-router-dom";
import { compose } from "redux";
import { withTranslation } from "react-i18next";

// @Functions
import tryConvert from "../../utils/changeMoney";
import numberWithCommas from "../../utils/formatPrice";
import {INITIAL_IMAGE} from '../../constants';

class ProductItem extends Component {
  onReload = (path) => {
    const {history} = this.props
    history.push(path);
    window.location.reload();
  }

  render() {
    const { product, currency, t } = this.props;
    return (
      <div className="col-lg-3 col-6 my-2">
        {product && <Link to={`/product/${product.pathseo}.${product.id}`} onClick={()=> this.onReload(`/product/${product.pathseo}.${product.id}`)} style={{textDecoration: 'none'}}>
        <div className="single-shop-product">
          <div className="product-upper text-center">
              <img
                src={
                  product.bigimage
                    ? product.bigimage.publicUrl
                    : INITIAL_IMAGE
                }
                className="h-100 w-auto"
                alt={`${product.name}`}
              />
          </div>
          <p className="text-dark mb-0 mt-2">
              {product.name.substring(0, 22)}
              {product.name.length > 22 ? "..." : ""}
          </p>
          <div className="product-carousel-price">
            {product.realPriceMin && product.realPriceMin > product.priceMin && 
            <div className="discount float-end">
              <div className="d-flex h-discount text-orange">
                <svg className="_2DRZW" viewBox="-0.5 -0.5 4 16">
                  <path d="M4 0h-3q-1 0 -1 1a1.2 1.5 0 0 1 0 3v0.333a1.2 1.5 0 0 1 0 3v0.333a1.2 1.5 0 0 1 0 3v0.333a1.2 1.5 0 0 1 0 3q0 1 1 1h3" strokeWidth="1" transform="" stroke="currentColor" fill="#f69113"></path>
                </svg>
            <div className="discount-content">{t('common.discount')} {parseInt((1 - product.priceMin/product.realPriceMin)*100)}%</div>
                <svg className="h-discount" viewBox="-0.5 -0.5 4 16">
                  <path d="M4 0h-3q-1 0 -1 1a1.2 1.5 0 0 1 0 3v0.333a1.2 1.5 0 0 1 0 3v0.333a1.2 1.5 0 0 1 0 3v0.333a1.2 1.5 0 0 1 0 3q0 1 1 1h3" strokeWidth="1" transform="rotate(180) translate(-3 -15)" stroke="currentColor" fill="#f69113">
                  </path>
                </svg>
              </div>
            </div>}
            <ins>
              {currency === "VND" && product.priceMin 
                ? numberWithCommas(product.priceMin)
                : numberWithCommas(
                    parseFloat(
                      tryConvert(product.priceMin, currency, false)
                    ).toFixed(2)
                  )}{" "}
              {currency}
            </ins><br/>
            {product.realPriceMin && product.realPriceMin > product.priceMin && <del>
              {currency === "VND" && product.realPriceMin 
                ? numberWithCommas(product.realPriceMin)
                : numberWithCommas(
                    parseFloat(
                      tryConvert(product.realPriceMin, currency, false)
                    ).toFixed(2)
                  )}{" "}
              {currency}
            </del>}
          </div>
        </div>
        </Link>}
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    currency: state.currency,
  };
};

const mapDispatchToProps = (dispatch, props) => {
  return {};
};

const withConnect = connect(mapStateToProps, mapDispatchToProps);

export default compose(
  withConnect, 
  withTranslation(),
  withRouter
)(ProductItem);

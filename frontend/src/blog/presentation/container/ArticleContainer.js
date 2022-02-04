import React from "react";
import {hideContent, showContent} from "../../application/actions";
import {connect} from "react-redux";
import Article from "../component/Article";


const mapStateToProps = (state) => {
    console.log(state);
    return {
        isContentVisible: state.isContentVisible,
        title: state.title,
        categoryName: state.categoryName,
        content: state.content
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        showContent: () => dispatch(showContent()),
        hideContent: () => dispatch(hideContent())
    }
}

const ArticleContainer = connect(
    mapStateToProps,
    mapDispatchToProps
) (Article);

export default ArticleContainer;
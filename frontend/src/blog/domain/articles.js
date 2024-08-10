export const fillTempLists = (values, articleList, visibilityList) => {
    const tempArticles = [...articleList];
    const tempVisibilities = [...visibilityList];

    values.map((article) => {
            tempArticles.push(article);
            tempVisibilities.push(false);
        }
    )

    return [tempArticles, tempVisibilities];
}

export const updateVisibility = (event, visibilityList) => {
    const tempVisibilities = [...visibilityList];
    const index = event.target.getAttribute('index');
    console.log(event.target, index)

    tempVisibilities[index] = !tempVisibilities[index];

    return tempVisibilities;
}

export const updatePage = (pageNum) => {
    return pageNum + 1;
}

export let articles = [];

export let visibilities = [];

export let page = 0;

export const fillTempLists = (values) => {
    const tempArticles = [...articles];
    const tempVisibilities = [];

    values.map((article) => {
            tempArticles.push(article);
            tempVisibilities.push(false);
        }
    )

    articles = tempArticles;
    visibilities = tempVisibilities;

    return [tempArticles, tempVisibilities];
}

export const updateVisibility = (event) => {
    const tempVisibilities = [...visibilities];
    const index = event.target.getAttribute('index');
    console.log(event.target, index)

    tempVisibilities[index] = !visibilities[index];
    visibilities = tempVisibilities;

    return tempVisibilities;
}

export const updatePage = () => {
    page += 1;
    return page;
}

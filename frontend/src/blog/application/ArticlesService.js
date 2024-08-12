import {Articles} from "../domain/Articles";

export function ArticlesService({url}) {

    return (
        <div>
            <Articles url={url}/>
        </div>
    )
}
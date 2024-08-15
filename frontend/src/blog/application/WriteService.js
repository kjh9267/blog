import {Write} from "../domain/Write";

export function WriteService({cookie}) {

    return (
        <div>
            <Write cookie={cookie}/>
        </div>
    )
}
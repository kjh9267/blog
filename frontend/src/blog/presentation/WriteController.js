import React from 'react';
import {WriteService} from "../application/WriteService";

function WriteController({cookie}) {

    return (
        <div>
            <WriteService cookie={cookie} />
        </div>
    )
}

export default WriteController;
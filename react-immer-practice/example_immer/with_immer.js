/**
 * With Immer
 * With Immer, this process is more straightforward.
 */

import { produce } from "immer";

const baseState = [
    {
        title: "Learn Typescript",
        done: true
    },
    {
        title: "Try immer",
        done: false
    }
];

// produce 함수를 사용할 때에는 첫번째 파라미터에는 수정하고 싶은 상태, 
// 두번째 파라미터에는 어떻게 업데이트를 하고 싶은지 정의하는 함수를 넣어준다.

// 두번째 파라미터에 넣는 함수에서는 불변성에 대해 신경쓰지 않고 그냥 업데이트 해주면 알아서 다 해준다. 
const nextState = produce(baseState, draft => {
    draft[1].done = true;
    draft.push({ title: "Tweet about it" });
});

console.log("nextState: ", nextState);


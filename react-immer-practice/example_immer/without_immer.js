/**
 * Without Immer
 * we'll have to carefully shallow copy every level of the state structure that is affected by our change:
 * Immer가 없다면, 변경에 영향을 받는 상태 구조의 모든 레벨을 신중히 얕은 복사를 해야합니다:
 */

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

const nextState = baseState.slice(); // 기존 배열을 얕은 복사하여 새로운 배열을 생성함.

// newState의 두번 째 요소를 얕은 복사하여 수정한 다음, 해당 요소의 'done' 속성을 true로 업데이트함. 
newState[1] = {
    ...newState[1], // with a shallow clone of element 1
    done: true // ...combined with the desired update
};

// 즉, 원본 배열(baseState)은 변경되지 않고, 새로운 배열(newState)에만 변경 사항이 적용됨.


// newState 배열에 새로운 객체 추가 -> push 사용
newState.push({ title: "Tweet about it" });

// nextState가 새로 복제되었기 떄문에 push를 사용하는게 안전하긴하다.

// 하지만, 미래의 어떤 임의의 시점에서 동일한 작업을 수행하는 것은 불변성 원칙을 위반하고 버그를 일으킬 수 잇따.

// 즉 ! 불변성을 유지하려면 배열을 수정할 때 항상 새로운 배열을 생성하고 수정하는 것이 좋다...!

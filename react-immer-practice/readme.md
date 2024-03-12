# react-immer-practice

## immer의 원리

immer의 핵심 원리는 Copy-on-write(기록 중 복사)와 Proxy(프록시)에 있다.

Copy-on-write(기록 중 복사)란, 자원을 공유하다가도 수정해야 할 경우가 발생하면 자원의 복사본을 쓰게 하는 개념이다.

immer는 프록시 객체를 이용해서 원본 객체인 상태 객체 대신 프록시 객체를 대신 조작하는 것이다.

### How Immer works

1. **임시 초안(temporary draft)에 변경 사항 적용**: Immer를 사용하면 변경 사항을 현재 상태(currentState)의 프록시인 임시 초안에 적용한다.

Temporary draft(임시 초안)는 currentState(현재 상태)의 proxy(프록시; 가상적인 복사본)으로 이해할 수 있다.

`The basic idea is that with immer you will apply your changes to temporary draft, which is a proxy of the currentState.`

2. **모든 변이가 완료되면 다음 상태 생성**: 모든 변이가 완료되면 Immer는 임시 초안(temporary draft) 상태에 대한 변이를 기반으로 다음 상태(nextState)를 생성한다.

이 과정에서 실제 데이터 구조를 변경하지 않고도 변경 사항을 추적하고 적용할 수 있다.

`Once all your mutations are completed, Immer will produce the nextState based on the mutations to the draft state`

3. **데이터 수정의 간편성과 불변성 데이터의 혜택 유지** : 이것은 데이터를 수정할 때 단순히 해당 데이터를 수정하여 변경 사항을 추적, 적용할 수 있으며 동시에 불변성 데이터의 모든 이점을 유지할 수 있음을 의미한다.

`This means that you can interact with your data by simply modifying it while keeping all the benefits of immutable data.`

### Array method

- Array.prototype.slice()

  - slice() 메서드는 어떤 배열의 begin부터 end까지(end 미포함)에 대한 **얕은 복사본을 새로운 배열 객체로 반환**합니다.
  - 원본 배열은 바뀌지 않습니다.
  - ref. https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/slice

- Array.prototype.push()
  - push() 메서드는 배열의 끝에 하나 이상의 요소를 추가하고, 배열의 새로운 길이를 반환합니다.
  - ref. https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/push

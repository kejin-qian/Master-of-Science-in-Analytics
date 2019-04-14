
# coding: utf-8

# In[5]:

import traceback,sys
#empty list

test1= []
#with reverse

#pure numbers:

test20 = [1,2,3,4,5,6,7,8]

test21 = [3,4,5,6,7,8]
test22 = [2]
test23 = [10,9,8,7,6,5,4,3,2,1]
test24 = [5,4,3,2,1]

#pure alpha:

test30 = ['a','b','c','d','e']

test31 = ['a','b']
test32 = ['e','d','c','b','a']

#with reverse

#combining number with alpha
test40=[90,'a','z','c']
key40=lambda x:sum([ord(item) for item in str(x)])

def print_error(actual, expected,desc):
    print('#'*100)
    incorrect_str = 'testing '+desc+' ------- INCORRECT'
    print(incorrect_str)
    print('expected:',end=' ')
    print(expected)
    print('got',end=' ')
    print(actual)
    #exc_type, exc_value, exc_traceback = sys.exc_info()
    #traceback.print_exception(exc_type, exc_value, exc_traceback,
    #limit=2, file=sys.stdout)
    print('#'*100)
    print()
    
def print_correct(desc):
    print('#'*100)
    correct_str = 'testing '+desc+' ------- CORRECT'
    print(correct_str)
    print('#'*100)
    print()

def test_correctness(func,test_itr,key,reverse,desc):
    #if the returned and the first element of the list is the same, then return 1.
    result = sorted(test_itr,key=key,reverse=reverse)
    correct=0
    try:
        test = func(test_itr,key=key,reverse=reverse)
    except:
        print_error(None,result,desc)
    
    try:
        assert(test == result)
        correct=1
    except:
        try:
            assert(test[0] == result)
            correct=1
        except:
            try:
                assert(test[-1] == result)
                correct=1
            except:
                print_error(func(test_itr,key=key,reverse=reverse),result,desc)
    finally:
        return correct
                

def test_case(func,test_itr,desc='',key=None,reverse=False):
    correct=0
    if key is None:
        key=lambda x:x
    correct = test_correctness(func,test_itr,key,reverse,desc)
    if correct:
        print_correct(desc)
    return correct

def runner_reverse(func,test,key=None):
    return test_case(func,test,desc='['+','.join([str(item) for item in test])+']',key=key,reverse=False) +     test_case(func,test,desc='reverse'+'['+','.join([str(item) for item in test])+']',key=key,reverse=True)

def grade_test_main(func):
    return 20-    runner_reverse(func,test1)-    runner_reverse(func,test20)-    runner_reverse(func,test21)-    runner_reverse(func,test22)-    runner_reverse(func,test23)-    runner_reverse(func,test24)-    runner_reverse(func,test30)-    runner_reverse(func,test31)-    runner_reverse(func,test32)-    runner_reverse(func,test40,key=key40)
print ('bubble incorrect cases: %d /20, merge incorrect cases: %d /20'% (grade_test_main(bubble_master),grade_test_main(merge_master)))


# In[ ]:




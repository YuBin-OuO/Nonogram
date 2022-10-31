
public class Solution {



	int[][] s1 = {
			{0,0,0,0,0,0,0,0,1,1,1,1,0,0,0},
			{0,0,0,0,0,0,0,1,1,1,1,1,1,0,0},
			{0,0,0,0,0,1,1,0,0,1,1,1,0,0,0},
			{0,0,0,0,0,0,1,1,0,0,0,0,0,0,0},
			{0,0,0,1,1,1,0,1,0,1,1,1,0,0,0},
			{0,0,1,1,1,1,1,1,1,1,1,1,1,0,0},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
			{0,1,1,1,0,1,1,1,1,1,1,1,1,1,0},
			{0,1,1,1,0,1,1,1,1,1,1,1,1,1,0},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
			{0,1,1,1,0,1,1,1,1,1,1,1,1,1,0},
			{0,0,1,1,1,1,1,1,1,1,1,1,1,0,0},
			{0,0,1,1,1,1,1,1,1,1,1,1,1,0,0},
			{0,0,0,1,1,1,1,1,1,1,1,1,0,0,0},
			{0,0,0,0,1,1,1,0,1,1,1,0,0,0,0}
	};	// ���
	
	int[][] s2 = {
			{0,0,0,0,0,0,0,0,0,1,1,1,0,0,0},
			{0,0,0,0,0,0,0,0,1,1,1,1,1,0,0},
			{0,0,0,0,0,0,0,1,1,1,1,0,1,1,1},
			{0,0,0,0,0,0,0,1,1,1,1,1,1,1,0},
			{0,0,0,0,0,0,0,0,1,1,1,1,1,0,0},
			{0,0,0,0,0,0,0,0,0,1,1,1,0,0,0},
			{0,0,0,0,0,0,0,0,1,1,1,1,1,0,0},
			{1,0,0,0,0,0,1,1,1,1,1,1,1,1,0},
			{1,1,1,0,0,1,1,1,0,0,0,1,1,1,0},
			{1,1,1,1,1,1,1,0,1,1,1,0,1,1,0},
			{0,1,1,1,1,1,0,1,1,1,1,0,1,1,0},
			{0,1,1,1,1,1,1,1,1,0,0,1,1,0,0},
			{0,0,1,1,1,1,1,1,1,1,1,1,0,0,0},
			{0,0,0,0,1,1,0,1,1,1,0,0,0,0,0},
			{0,0,0,0,0,0,1,1,1,1,1,1,0,0,0}
	};	// ����
	
	
	int[][] s3 = {
			{1,1,0,1,1,0,0,0,0,0,0,1,1,0,1},
			{1,1,0,1,1,0,1,0,0,1,0,1,1,0,1},
			{0,1,1,1,1,0,1,0,0,1,0,1,1,0,1},
			{0,0,1,1,1,1,1,0,0,0,1,1,1,1,1},
			{0,0,0,0,1,1,0,1,1,1,1,1,0,0,0},
			{0,0,0,0,0,0,1,1,1,1,1,1,1,0,0},
			{0,0,0,0,0,1,0,1,0,1,1,1,1,1,0},
			{0,0,0,0,1,1,1,1,1,1,1,1,1,1,1},
			{0,0,0,1,1,1,1,1,1,1,1,1,1,1,1},
			{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{0,1,1,1,1,1,1,1,1,0,1,1,1,1,1},
			{0,1,0,1,1,1,0,0,0,0,1,1,1,1,1},
			{0,1,1,1,1,1,0,0,0,0,1,1,1,1,1},
			{0,0,1,1,1,0,0,0,0,1,1,1,1,1,1},
			{0,0,0,0,0,0,0,0,0,1,1,1,1,1,1}
	};	// �罿
	
	int[][] s4 = {
			{0,0,0,0,0,1,1,0,1,1,0,0,0,0,0},
			{0,0,0,0,1,1,1,1,1,1,1,0,0,0,0},
			{0,0,0,0,1,1,1,1,1,1,1,0,0,0,0},
			{0,0,0,0,1,1,1,1,1,1,1,0,0,0,0},
			{0,1,1,1,0,1,1,1,1,1,0,1,1,1,0},
			{1,1,1,1,1,0,1,1,1,0,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,0,1,1,1,1,1,1},
			{0,1,1,1,1,1,1,0,1,1,1,1,1,1,0},
			{1,1,1,1,1,1,0,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,0,0,1,0,0,1,1,1,1,1},
			{0,1,1,1,0,0,0,1,0,0,0,1,1,1,0},
			{0,0,0,0,0,0,1,1,0,0,0,0,0,0,0},
			{0,0,0,0,0,1,1,0,0,0,0,0,0,0,0},
			{0,0,1,1,1,1,0,0,0,0,0,0,0,0,0},
			{0,0,1,1,0,0,0,0,0,0,0,0,0,0,0}
	};	// ����Ŭ�ι�
	
	int[][] s5 = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,0,1,1,1,1,1,1,1},
			{1,1,0,1,1,1,1,0,1,1,1,1,0,1,1},
			{1,1,1,0,1,1,1,1,1,1,1,0,1,1,1},
			{1,1,1,1,1,0,0,0,0,0,1,1,1,1,1},
			{1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
			{1,1,1,1,0,0,1,0,1,0,0,1,1,1,1},
			{1,0,0,1,0,0,1,0,1,0,0,1,0,0,1},
			{1,1,1,1,0,1,0,0,0,1,0,1,1,1,1},
			{1,1,1,1,0,0,1,1,1,0,0,1,1,1,1},
			{1,1,1,1,1,0,0,0,0,0,1,1,1,1,1},
			{1,1,1,0,1,1,1,1,1,1,1,0,1,1,1},
			{1,1,0,1,1,1,1,0,1,1,1,1,0,1,1},
			{1,1,1,1,1,1,1,0,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
	};	// ��

	int[][] s6 = {
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,1,0,1,1,0,0,0,0,1,0,0,0,0},
			{1,1,1,1,1,1,1,0,1,1,1,1,0,0,1},
			{0,1,1,0,0,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,0,0,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,0,0,0,0,0,1,1,1,1,1,0,0},
			{1,1,0,0,0,0,0,0,0,1,1,0,0,0,1},
			{1,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,0,0,0,0,0,0,0,0,1,0,0,1,1},
			{1,1,1,1,0,0,0,0,1,1,1,1,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
	};	// ������
	
	int[][] s7 = {
			{0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,1,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,1,1,0,0,0,0,0,0},
			{1,1,0,1,1,0,1,0,0,0,1,1,0,1,1},
			{1,1,1,1,1,0,1,0,0,0,1,1,1,1,1},
			{1,0,0,0,1,0,1,0,0,0,1,0,0,0,1},
			{1,0,1,0,1,0,1,0,0,0,1,0,1,0,1},
			{1,0,1,0,1,1,1,1,1,1,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,1,0,0,0,0,0,0,0,1,0,0,1},
			{1,0,1,1,1,0,0,1,0,0,1,1,1,0,1},
			{1,0,0,1,0,0,1,1,1,0,0,1,0,0,1},
			{1,0,0,1,0,0,1,1,1,0,0,1,0,0,1},
			{1,0,0,0,0,0,1,1,1,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
	};	// ��
	
	int[][] s8 = {
			{0,0,0,0,1,1,1,1,0,0,0,0,0,0,0},
			{0,0,0,0,1,0,0,1,0,0,0,0,0,0,0},
			{0,0,0,1,1,1,1,1,1,1,0,0,0,0,0},
			{0,1,1,1,1,1,1,1,1,1,1,0,0,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,0,1,1,1,1,1,1,1,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,0,1},
			{1,1,1,0,0,0,1,1,1,1,0,1,1,1,1},
			{1,1,1,0,0,0,0,0,1,1,1,1,1,1,0},
			{1,1,1,0,0,0,1,0,0,0,1,1,1,1,0},
			{1,1,1,0,0,0,0,0,0,0,1,1,1,1,0},
			{0,1,1,0,0,0,0,0,0,0,1,1,1,1,0},
			{0,0,1,1,0,0,0,0,0,0,1,1,1,0,0},
			{0,0,0,1,1,1,1,1,1,1,1,1,0,0,0},
			{0,0,0,0,1,1,1,1,1,1,1,0,0,0,0}
	};	// ��
	
	int[][] s9= {
			{0,0,1,0,0,0,0,0,1,0,0,0,0,0,0},
			{0,0,1,1,1,1,1,1,1,1,0,0,0,0,0},
			{0,1,0,0,0,1,0,0,0,1,1,0,0,0,0},
			{0,1,0,1,0,0,0,1,0,1,1,0,0,0,0},
			{0,1,0,0,0,1,0,0,0,1,1,1,0,0,0},
			{0,1,1,1,1,0,1,1,1,1,1,1,0,0,0},
			{1,1,0,1,1,0,1,1,0,1,1,1,1,0,0},
			{1,1,0,0,1,1,1,0,0,1,1,0,1,1,0},
			{1,1,0,0,0,0,0,0,0,0,1,1,1,1,1},
			{1,1,0,0,0,0,0,0,0,0,1,0,1,0,1},
			{0,1,1,0,0,0,0,0,0,1,1,1,1,1,1},
			{0,0,1,1,1,0,0,0,1,1,0,1,1,0,1},
			{0,0,0,1,1,1,0,0,1,1,0,1,1,1,1},
			{0,0,0,0,1,1,1,1,1,1,0,1,1,1,0},
			{0,0,0,0,0,1,0,0,1,0,0,1,1,0,0}
	};	// �û���
	
	int[][] s10 = {
			{1,1,1,1,1,1,1,0,0,0,0,0,0,0,0},
			{1,1,1,1,1,1,1,0,0,0,0,0,0,0,0},
			{1,1,0,0,0,1,1,1,1,1,1,0,0,0,0},
			{1,1,0,0,0,1,1,1,1,1,1,1,1,1,1},
			{1,1,0,0,0,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,0,0,1},
			{0,0,0,1,0,1,1,1,1,1,1,1,0,0,1},
			{0,0,0,1,1,1,1,1,0,1,1,1,1,1,1},
			{0,0,0,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,0,0,0,1,1,1,1,1,0,1,1,1,1},
			{1,1,1,1,1,1,1,0,0,0,0,0,1,1,1},
			{0,1,1,1,1,1,0,0,0,0,0,0,0,0,0},
			{0,0,1,1,1,0,0,0,0,0,0,0,0,0,0}
	}; // �ڳ���
}

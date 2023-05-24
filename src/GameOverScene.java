package matching_cards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOverScene extends JPanel {
    private static class ImagePanel extends JPanel {
        private Image image;
        private float alpha = 0.0f;

        public ImagePanel(Image image) {
            this.image = image;
            setLayout(null);
        }

        public void setAlpha(float alpha) {
            this.alpha = Math.max(0.0f, Math.min(1.0f, alpha));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            g2d.dispose();
        }
    }

    private ImagePanel backgroundPanel;
    private Timer fadeInTimer;
    private float alpha = 0.0f;

    public Main main; // Main 클래스의 인스턴스 변수
    public int time; // int 값의 변수

    public GameOverScene(Main main, int time) {
        this.main = main;
        this.time = time;

        setLayout(new BorderLayout());

        // 배경 이미지 패널
        backgroundPanel = new ImagePanel(new ImageIcon("src/image/gameover2.jpg").getImage());
        backgroundPanel.setLayout(new BorderLayout());

        add(backgroundPanel);

        // 이미지 페이드 인 효과 타이머
        fadeInTimer = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (alpha < 1.0f) {
                    alpha += 0.01f;
                    backgroundPanel.setAlpha(alpha);
                    backgroundPanel.repaint();
                } else {
                    fadeInTimer.stop();
                }
            }
        });
        fadeInTimer.start();

        // 닉네임 입력 패널
        JPanel nicknamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        nicknamePanel.setOpaque(false);

        JLabel rankLabel = new JLabel("축하합니다! 당신의 기록은 " + time + "초입니다.");
        rankLabel.setFont(new Font("Maplestory Bold", Font.PLAIN, 30));
        rankLabel.setForeground(Color.BLACK);

        // 이미지 하단 가운데에 위치하기 위한 추가 코드
        backgroundPanel.setLayout(new BorderLayout());
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        centerPanel.add(rankLabel);
        backgroundPanel.add(centerPanel, BorderLayout.PAGE_END);

        add(backgroundPanel);

        // 닉네임 레이블
        JLabel nicknameLabel = new JLabel("Enter Nickname:");
        nicknameLabel.setFont(new Font("Maplestory Bold", Font.PLAIN, 27));
        nicknameLabel.setForeground(Color.BLACK);
        nicknamePanel.add(nicknameLabel);

        // 닉네임 입력 필드
        JTextField nicknameField = new JTextField(15);
        nicknameField.setFont(new Font("Maplestory Bold", Font.PLAIN, 24));
        nicknameField.setBackground(Color.BLACK);
        nicknameField.setForeground(Color.WHITE);
        nicknameField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nickname = nicknameField.getText();
                // 순위표를 표시하는 새로운 창을 열고, 닉네임과 함께 전달
                displayRankingWindow(nickname);
            }
        });
        nicknamePanel.add(nicknameField);

        add(nicknamePanel, BorderLayout.SOUTH);
    }

    private void displayRankingWindow(String nickname) {
        // LeaderboardScene 클래스 내부에 생성자를 추가하여 닉네임을 전달할 수 있도록 합니다.
        class LeaderboardScene extends JPanel {
            private String nickname; // 닉네임 변수

            public LeaderboardScene(String nickname) {
                this.nickname = nickname;
                initUI();
            }

            private void initUI() {
                // 순위표를 표시하는 UI 초기화 로직을 작성

                // 닉네임 활용 예시
                JLabel nicknameLabel = new JLabel("닉네임: " + nickname);
                add(nicknameLabel);
            }
        }

            JFrame rankingWindow = new JFrame("Ranking");
            rankingWindow.setSize(700, 700);
            rankingWindow.setLocationRelativeTo(this);
            rankingWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JTextArea rankingTextArea = new JTextArea();
            rankingTextArea.setFont(new Font("Maplestory Bold", Font.PLAIN, 18));
            rankingTextArea.setEditable(false);

            // 순위표 내용 작성 (임시)
            String rankingText = "Nickname: " + nickname + "\n";
            rankingText += "1. Player A\n";
            rankingText += "2. Player B\n";
            rankingText += "3. Player C\n";
            rankingTextArea.setText(rankingText);

            rankingWindow.add(rankingTextArea, BorderLayout.CENTER);

            JLabel rankingLabel = new JLabel("Your Ranking: " + getRankingByTime() + ". " + nickname);
            rankingLabel.setFont(new Font("Maplestory Bold", Font.PLAIN, 24));
            rankingLabel.setForeground(Color.RED);

            // 순위표 및 강조 레이블을 담을 패널
            JPanel rankingPanel = new JPanel(new BorderLayout());
            rankingPanel.add(rankingLabel, BorderLayout.SOUTH);
            rankingPanel.add(rankingTextArea, BorderLayout.CENTER);

            rankingWindow.add(rankingPanel, BorderLayout.CENTER);

            // 종료 버튼
            JButton exitButton = new JButton("종료");
            exitButton.setFont(new Font("Maplestory Bold", Font.PLAIN, 18));
            exitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // 프로그램 종료
                    System.exit(0);
                }
            });

            // 처음으로 버튼
            JButton restartButton = new JButton("처음으로");
            restartButton.setFont(new Font("Maplestory Bold", Font.PLAIN, 18));
            restartButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // 게임 초기화 로직을 작성
                    // 예: 게임 화면을 초기화하고 처음부터 시작하는 등의 동작
                    main.setTitleScene(); // Main 클래스의 setTitleScene() 메서드 호출
                }
            });

            // 버튼 패널
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(restartButton);
            buttonPanel.add(exitButton);
            rankingWindow.add(buttonPanel, BorderLayout.SOUTH);

            rankingWindow.setVisible(true);
        }
    private int getRankingByTime() {
        // 시간을 기반으로 등수 계산하는 로직을 작성
        // 예: 게임 플레이 시간을 가져와 순위 계산 후 반환
        // 임시로 랜덤 등수 반환하는 코드
        return (int) (Math.random() * 10) + 1;
    }
}
